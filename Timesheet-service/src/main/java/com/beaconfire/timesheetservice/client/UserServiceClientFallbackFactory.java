package com.beaconfire.timesheetservice.client;

import org.springframework.cloud.openfeign.FallbackFactory;

public class UserServiceClientFallbackFactory implements FallbackFactory<UserServiceClient> {
    @Override
    public UserServiceClient create(Throwable cause) {
        return new UserServiceClientFallback(cause);
    }
}

