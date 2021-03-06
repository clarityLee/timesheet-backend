package com.beaconfire.timesheetservice.controller;

import com.beaconfire.timesheetservice.client.UserServiceClient;
import com.beaconfire.timesheetservice.constant.Constant;
import com.beaconfire.timesheetservice.domain.TimeSheet;
import com.beaconfire.timesheetservice.domain.UpdateTimesheetRequest;
import com.beaconfire.timesheetservice.security.JwtUtil;
import com.beaconfire.timesheetservice.service.TimesheetService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/timesheet-service")
public class TimesheetController {

    private TimesheetService timeSheetService;
    @Autowired
    public void setTimeSheetService(TimesheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<TimeSheet> postTimesheet(@RequestBody TimeSheet timeSheet, HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
        if (username == null) {
            return ResponseEntity.ok(new TimeSheet());
        }
        String weekEnding = timeSheet.getWeekEnding().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ResponseEntity.ok(timeSheetService.saveTimeSheet(username, weekEnding, timeSheet));
    }

    @GetMapping(value = "/read")
    public ResponseEntity<TimeSheet> getTimesheetByUsernameAndWeekEnding(@RequestParam String username, @RequestParam String weekEnding) {
        TimeSheet timeSheet = timeSheetService.getTimeSheetByUsernameAndWeekEnding(username, weekEnding);
        if (timeSheet == null) {
            return new ResponseEntity<>(new TimeSheet(), HttpStatus.OK);
        }
        return ResponseEntity.ok(timeSheet);
    }

    @GetMapping(value = "/getAllList")
    public ResponseEntity<List<TimeSheet>> getAllTimeSheetList(HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
        return username == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(timeSheetService.getAllListByUsername(username), HttpStatus.OK);
    }


    @PostMapping(value = "/saveFile")
    public ResponseEntity<Void> uploadFile(
            HttpServletRequest httpServletRequest,
            @RequestParam String weekEnding,
            @RequestParam String uploadType,
            @RequestParam MultipartFile file) throws IOException {

        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
        return new ResponseEntity<>(
                username == null || !timeSheetService.appendFile(username, weekEnding, uploadType, file)
                ? HttpStatus.BAD_REQUEST : HttpStatus.OK
        );
    }

}