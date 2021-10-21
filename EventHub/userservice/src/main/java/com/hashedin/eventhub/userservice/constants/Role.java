package com.hashedin.eventhub.userservice.constants;

public enum Role {

    ADMIN("ADMIN"),
    MEMBER("MEMBER");

     public String role;

    private Role(String role) {
        this.role = role;
    }
}
