package com.beaconfire.auth.client;

import com.beaconfire.auth.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserServiceClientFallback implements UserServiceClient {

    private final Throwable cause;

    public UserServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<User> login(String username, String password) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new User());
    }
}
