package com.hashedin.eventhub.eventservice.dto;

import com.hashedin.eventhub.eventservice.constants.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private boolean isRegistered;
}
