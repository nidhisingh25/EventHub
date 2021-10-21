package com.hashedin.eventhub.userservice.entity;

import com.hashedin.eventhub.userservice.constants.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="USER_DETAILS")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="NAME", nullable = false, length = 64)
    private String name;

    @Column(name="EMAIL", nullable = false, length = 64 ,unique = true)
    private String email;

    @Column(name="PASSWORD", nullable = false, length = 64)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="ROLE",nullable = false, length = 10)
    private Role role;

    @Column(name="IS_REGISTERED", length = 64)
    private boolean isRegistered;
}
