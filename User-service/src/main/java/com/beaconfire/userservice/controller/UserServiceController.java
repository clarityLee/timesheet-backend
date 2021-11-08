package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.constant.Constant;
import com.beaconfire.userservice.domain.TimeSheet;
import com.beaconfire.userservice.domain.UpdateTimesheetRequest;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.security.JwtUtil;
import com.beaconfire.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/user-service")
public class UserServiceController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // ↓↓ ------------ Yun-Jing ------------ ↓↓
    @PostMapping("/login")
    public ResponseEntity<User> login(String username, String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> randomNewUser(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.createRandomUser(username, password));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.queryUserById(id));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser( HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
        return ResponseEntity.ok(userService.queryUserByName(username));
    }

    // ↓↓------------- Cynthia --------------------- ↓↓
    @PostMapping(value = "/update-profile")
    public ResponseEntity<User> updateProfile(@RequestBody Map<String, String> updatedInfo, HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
//        String username = "john";
        User updatedUserProfile = userService.updateUserProfile(username, updatedInfo);
        return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
    }

    @GetMapping(value = "/user-profile")
    public ResponseEntity<User> getUserProfile(HttpServletRequest httpServletRequest) {
        String username = JwtUtil.getSubject(httpServletRequest, Constant.JWT_TOKEN_COOKIE_NAME, Constant.SIGNING_KEY);
//        String username = "john";
        User user = userService.queryUserByName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/update-timesheet")
    public ResponseEntity<Void> updateTimesheetByUsername(@RequestBody UpdateTimesheetRequest updateTimesheetRequest) {
        String username = updateTimesheetRequest.getUsername();
        List<TimeSheet> timesheets = updateTimesheetRequest.getTimeSheetList();
        System.out.println("successfully in user service client: " + username);
        User user = userService.updateTimesheetByUsername(username, timesheets);
        return new ResponseEntity<>(HttpStatus.OK);
//        return null;
    }

    // ↓↓ -------------- Xian ------------------------ ↓↓



}
