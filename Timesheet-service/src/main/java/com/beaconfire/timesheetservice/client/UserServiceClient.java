package com.beaconfire.timesheetservice.client;

import com.beaconfire.timesheetservice.domain.TimeSheet;
import com.beaconfire.timesheetservice.domain.UpdateTimesheetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import feign.RequestLine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "userService", url = "http://localhost:9000",
        fallbackFactory = UserServiceClientFallbackFactory.class)
public interface UserServiceClient {

    @RequestLine("POST")
    @PostMapping("/user-service/update-timesheet")
    ResponseEntity<Void> updateTimesheetByUsername(@RequestBody UpdateTimesheetRequest updateTimesheetRequest);
}
