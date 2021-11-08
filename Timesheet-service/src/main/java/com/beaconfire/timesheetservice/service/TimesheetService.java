package com.beaconfire.timesheetservice.service;

import com.beaconfire.timesheetservice.client.UserServiceClient;
import com.beaconfire.timesheetservice.constant.ApproveStatus;
import com.beaconfire.timesheetservice.constant.SubmissionStatus;
import com.beaconfire.timesheetservice.dao.TimeSheetRepository;
import com.beaconfire.timesheetservice.domain.TimeSheet;
import com.beaconfire.timesheetservice.domain.UpdateTimesheetRequest;
import com.sun.istack.NotNull;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetService {

    private TimeSheetRepository timeSheetRepository;
    private UserServiceClient userServiceClient;
    @Autowired
    public void setTimeSheetRepository(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }
    @Autowired
    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    // ↓↓------------- Cynthia --------------------- ↓↓
    public TimeSheet createNewTimeSheet(TimeSheet timesheet, String username) {
        timesheet.setUsername(username);
        timesheet.setApprovalStatus(ApproveStatus.Not_Approved.toString());
        timesheet.setSubmissionStatus(SubmissionStatus.Incomplete.toString());
        timesheet.setSubmissionInfo("Items due: Proof of Approved TimeSheet");
        return timeSheetRepository.save(timesheet);
    }

    public List<TimeSheet> getTopFiveTimesheetByUsername(String username) {
        List<TimeSheet> allByUsername = timeSheetRepository.findByUsername(username);

        allByUsername.sort((timeSheet1, timeSheet2) -> {
            LocalDate time1 = timeSheet1.getWeekEnding();
            LocalDate time2 = timeSheet2.getWeekEnding();
            if (time1.isBefore(time2)) {
                return 1;
            } else {
                return -1;
            }
        });

        return allByUsername.stream().limit(5).collect(Collectors.toList());
    }

    public TimeSheet getTimeSheetByUsernameAndWeekEnding(String username, String weekEnding) {
        return timeSheetRepository.findByUsernameAndWeekEnding(username, LocalDate.parse(weekEnding));
    }

    // ↓↓------------- Yun-Jing --------------------- ↓↓

    public TimeSheet updateTimeSheet(TimeSheet old, TimeSheet fresh) {
        old.setBillingHours(fresh.getBillingHours());
        old.setCompensatedHours(fresh.getCompensatedHours());

        old.setSubmissionStatus(fresh.getSubmissionStatus());
        old.setSubmissionInfo(fresh.getSubmissionInfo());
        old.setApprovalStatus(fresh.getApprovalStatus());

        old.setComment(fresh.getComment());
        old.setCommentInfo(fresh.getCommentInfo());
        old.setUploadType(fresh.getUploadType());

        old.setFloatingRequired(fresh.getFloatingRequired());
        old.setVacationRequired(fresh.getVacationRequired());
        old.setUploadFile(fresh.getUploadFile());
        old.setDayDetails(fresh.getDayDetails());
        return timeSheetRepository.save(old);
    }

    public boolean appendFile(String username, String weekEnding,
                              String uploadType, MultipartFile file) throws IOException {

        TimeSheet timeSheet = timeSheetRepository.findByUsernameAndWeekEnding(username, LocalDate.parse(weekEnding));
        if (timeSheet == null || timeSheet.getId() == null)
            return false;

        timeSheet.setUploadType(uploadType);
        if ("approved".equals(uploadType)) {
            timeSheet.setSubmissionStatus(SubmissionStatus.Complete.toString());
            timeSheet.setSubmissionInfo("");
        }
        timeSheet.setUploadFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        timeSheetRepository.save(timeSheet);

        updateFiveTimeSheetToUser(username, 0, 0);
        return true;
    }

    public List<TimeSheet> getAllListByUsername(String username) {
        Sort sort = Sort.by(Sort.Direction.DESC,"weekEnding");
        return timeSheetRepository.findByUsername(username, sort);
    }

    public TimeSheet saveTimeSheet(String username, String weekEnding, TimeSheet timeSheet) {
        TimeSheet found = timeSheetRepository.findByUsernameAndWeekEnding(username, LocalDate.parse(weekEnding));

        int floatingChange = 0;
        int vacationChange = 0;
        TimeSheet newTimesheet;
        if (found == null || found.getId() == null) {
            floatingChange = -timeSheet.getFloatingRequired();
            vacationChange = -timeSheet.getVacationRequired();
            newTimesheet = createNewTimeSheet(timeSheet, username);
        } else {
            floatingChange = found.getFloatingRequired() - timeSheet.getFloatingRequired();
            vacationChange = found.getVacationRequired() - timeSheet.getVacationRequired();
            newTimesheet = updateTimeSheet(found, timeSheet);
        }


        updateFiveTimeSheetToUser(username, floatingChange, vacationChange);
        return newTimesheet;
    }

    public void updateFiveTimeSheetToUser(String username, @NotNull Integer floatingChange, @NotNull Integer vacationChange) {

        // get top five timeSheets
        List<TimeSheet> topFiveTimesheetByUsername = getTopFiveTimesheetByUsername(username);

        // update timeSheet in user
        UpdateTimesheetRequest updateTimesheetRequest = new UpdateTimesheetRequest();
        updateTimesheetRequest.setUsername(username);
        updateTimesheetRequest.setFloatingChange(floatingChange);
        updateTimesheetRequest.setVacationChange(vacationChange);
        updateTimesheetRequest.setTimeSheetList(topFiveTimesheetByUsername);
        userServiceClient.updateTimesheetByUsername(updateTimesheetRequest);
    }
}
