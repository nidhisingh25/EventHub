package com.hashedin.eventhub.eventservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    private LocalDateTime timestamp;

    private int status;

    private String message;

    private String path;
}


