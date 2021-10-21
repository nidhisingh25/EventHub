package com.hashedin.eventhub.eventservice.constants;

public enum Role {

    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    public String role;

    private Role(String role) {
        this.role = role;
    }
}
