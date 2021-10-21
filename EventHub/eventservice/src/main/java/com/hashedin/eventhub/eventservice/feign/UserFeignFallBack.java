package com.hashedin.eventhub.eventservice.feign;

import com.hashedin.eventhub.eventservice.dto.UserDetailsDto;
import org.springframework.web.bind.annotation.RequestHeader;

public class UserFeignFallBack implements  UserFeign {
    @Override
    public UserDetailsDto getUser(@RequestHeader("Authorization") String token, String email) {
        return null;
    }
}
