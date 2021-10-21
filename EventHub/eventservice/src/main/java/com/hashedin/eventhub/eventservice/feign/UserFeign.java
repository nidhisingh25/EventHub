package com.hashedin.eventhub.eventservice.feign;

import com.hashedin.eventhub.eventservice.dto.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user-service", fallback = UserFeignFallBack.class)
public interface UserFeign {

    @GetMapping("/user/username/{email}")
    public UserDetailsDto getUser(@RequestHeader("Authorization") String token,
            @PathVariable String email);
}
