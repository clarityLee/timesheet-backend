package com.beaconfire.timesheetservice.client;

import com.beaconfire.timesheetservice.domain.TimeSheet;
import com.beaconfire.timesheetservice.domain.UpdateTimesheetRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserServiceClientFallback implements UserServiceClient {

    private final Throwable cause;

    public UserServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<Void> updateTimesheetByUsername(UpdateTimesheetRequest updateTimesheetRequest) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
