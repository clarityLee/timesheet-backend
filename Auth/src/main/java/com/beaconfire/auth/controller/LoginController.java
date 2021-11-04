package com.beaconfire.auth.controller;

import com.beaconfire.auth.client.UserServiceClient;
import com.beaconfire.auth.domain.User;
import com.beaconfire.auth.security.CookieUtil;
import com.beaconfire.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final int COOKIE_DURATION_SECONDS = 24 * 60 * 60;
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";

    private UserServiceClient userServiceClient;

    @Autowired
    private void setUserServiceClient(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> singleSignOn(HttpServletResponse response, @RequestBody Map<String, Object> payLoad) {

        String username = (String) payLoad.get("username");
        String password = (String) payLoad.get("password");
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        ResponseEntity<User> res = userServiceClient.login(username, password);

        if (Objects.requireNonNull(res.getBody()).getId() == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String token = JwtUtil.generateToken(signingKey, username, Objects.requireNonNull(res.getBody()).getId().toString());
        CookieUtil.create(response, jwtTokenCookieName, token, false,
                COOKIE_DURATION_SECONDS, "localhost");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
