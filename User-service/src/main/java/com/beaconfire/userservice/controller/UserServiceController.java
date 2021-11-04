package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.constant.Constant;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.security.JwtUtil;
import com.beaconfire.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user-service")
public class UserServiceController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // ↓↓ ------------ Yun-Jing ------------ ↓↓
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> randomNewUser(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.createRandomUser(username, password));
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






    // ↓↓ -------------- Xian ------------------------ ↓↓



}
