package com.hashedin.eventhub.userservice.controller;

import com.hashedin.eventhub.userservice.config.AppProperties;
import com.hashedin.eventhub.userservice.dto.UserDetailsDto;
import com.hashedin.eventhub.userservice.response.BaseResponse;
import com.hashedin.eventhub.userservice.service.UserOperationService;
import com.hashedin.eventhub.userservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserOperationController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserOperationService userOperationService;

    @PostMapping(value = "${app.permit-signup-url}")
    public ResponseEntity<Object> addUser(@RequestBody UserDetailsDto userDetailsDto) {
        userDetailsDto.setRegistered(true);
        userOperationService.addUser(userDetailsDto);
        return new ResponseEntity<>(AppUtils.prepareSingleDataResponse(userDetailsDto,HttpStatus.OK,
                "Successfully added user", appProperties.getPermitSignupUrl()),HttpStatus.OK);
    }

    @GetMapping("/userid/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Long userId) {
        UserDetailsDto userDetailsDto = userOperationService.getUser(userId);
        return new ResponseEntity<>(AppUtils.prepareSingleDataResponse(userDetailsDto,HttpStatus.OK,
                "Successfully retrieved data", "/user/".concat(String.valueOf(userId))),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody  Map<String, String> emailParam) {
        UserDetailsDto userDetailsDto = userOperationService.getUser(emailParam.get("email"));
        userDetailsDto.setRegistered(true);
        userOperationService.addUser(userDetailsDto);
        return new ResponseEntity<>(new BaseResponse(LocalDateTime.now(),HttpStatus.OK.value(),
                "User successfully registered","/register"), HttpStatus.OK);
    }

    @GetMapping("/username/{email}")
    public ResponseEntity<UserDetailsDto> getUserFromUsername(@PathVariable String email) {
        UserDetailsDto userDetailsDto = userOperationService.getUser(email);
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }
}
