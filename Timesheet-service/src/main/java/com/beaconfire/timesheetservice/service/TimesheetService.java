package com.beaconfire.timesheetservice.service;

import com.beaconfire.timesheetservice.constant.ApproveStatus;
import com.beaconfire.timesheetservice.constant.SubmissionStatus;
import com.beaconfire.timesheetservice.dao.TimeSheetRepository;
import com.beaconfire.timesheetservice.domain.TimeSheet;
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
    @Autowired
    public void setTimeSheetRepository(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
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
        old.setDayDetails(fresh.getDayDetails());
        old.setUploadFile(fresh.getUploadFile());
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
        return true;
    }

    public List<TimeSheet> getAllListByUsername(String username) {
        Sort sort = Sort.by(Sort.Direction.DESC,"weekEnding");
        return timeSheetRepository.findByUsername(username, sort);
    }
}
