package com.beaconfire.auth.client;

import com.beaconfire.auth.domain.User;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userService", url = "http://localhost:9000",
        fallbackFactory = UserServiceClientFallbackFactory.class)
public interface UserServiceClient {

    @RequestLine("POST")
    @PostMapping("/user-service/login")
    ResponseEntity<User> login(@RequestParam String username, @RequestParam String password);

}
