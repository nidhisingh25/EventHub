package com.hashedin.eventhub.userservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginBodyDto {

    private String email;

    private String password;
}
