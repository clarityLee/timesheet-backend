package com.beaconfire.timesheetservice.service;

import com.beaconfire.timesheetservice.dao.TimeSheetRepository;
import com.beaconfire.timesheetservice.domain.TimeSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public TimeSheet createNewTimesheet(TimeSheet timesheet, String username) {
        timesheet.setUsername(username);
        System.out.println(timesheet);
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

    public TimeSheet getTimesheetByUsernameAndWeekEnding(String username, String weekEnding) {
        return timeSheetRepository.findByUsernameAndWeekEnding(username, LocalDate.parse(weekEnding));
    }

    // ↓↓------------- Yun-Jing --------------------- ↓↓

    public TimeSheet updateTimesheet(TimeSheet old, TimeSheet fresh) {
        old.setBillingHours(fresh.getBillingHours());
        old.setCompensatedHours(fresh.getCompensatedHours());
        old.setSubmissionStatus(fresh.getSubmissionStatus());
        old.setSubmissionInfo(fresh.getSubmissionInfo());
        old.setApprovalStatus(fresh.getApprovalStatus());
        old.setComment(fresh.getComment());
        old.setCommentInfo(fresh.getCommentInfo());
        old.setDayDetails(fresh.getDayDetails());
        return timeSheetRepository.save(old);
    }
}
