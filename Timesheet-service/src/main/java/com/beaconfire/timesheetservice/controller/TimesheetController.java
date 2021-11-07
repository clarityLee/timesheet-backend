package com.beaconfire.timesheetservice.controller;

import com.beaconfire.timesheetservice.client.UserServiceClient;
import com.beaconfire.timesheetservice.constant.Constant;
import com.beaconfire.timesheetservice.domain.TimeSheet;
import com.beaconfire.timesheetservice.domain.UpdateTimesheetRequest;
import com.beaconfire.timesheetservice.security.JwtUtil;
import com.beaconfire.timesheetservice.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/timesheet-service")
public class TimesheetController {

    private TimesheetService timesheetService;
    private UserServiceClient userServiceClient;
    @Autowired
    public void setTimesheetService(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }
    @Autowired
    public void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<TimeSheet> postTimesheet(@RequestBody TimeSheet timesheet, HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
        if (username == null) {
            return ResponseEntity.ok(new TimeSheet());
        }

        String weekEnding = timesheet.getWeekEnding().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        TimeSheet found = timesheetService.getTimesheetByUsernameAndWeekEnding(username, weekEnding);

        TimeSheet newTimesheet = null;
        if (found == null || found.getId() == null) {
            newTimesheet = timesheetService.createNewTimesheet(timesheet, username);
        } else {
            newTimesheet = timesheetService.updateTimesheet(found, timesheet);
        }

//        get top five timesheets
        List<TimeSheet> topFiveTimesheetByUsername = timesheetService.getTopFiveTimesheetByUsername(username);
//        update timesheet in user
        UpdateTimesheetRequest updateTimesheetRequest = new UpdateTimesheetRequest();
        updateTimesheetRequest.setUsername(username);
        updateTimesheetRequest.setTimeSheetList(topFiveTimesheetByUsername);
        userServiceClient.updateTimesheetByUsername(updateTimesheetRequest);
        return ResponseEntity.ok(newTimesheet);
    }

    @GetMapping(value = "/read")
    public ResponseEntity<TimeSheet> getTimesheetByUsernameAndWeekEnding(@RequestParam String username, @RequestParam String weekEnding) {
        TimeSheet timesheet = timesheetService.getTimesheetByUsernameAndWeekEnding(username, weekEnding);
        if (timesheet == null) {
            return new ResponseEntity<>(new TimeSheet(), HttpStatus.OK);
        }
        return ResponseEntity.ok(timesheet);
    }

}