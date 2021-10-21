package com.hashedin.eventhub.eventservice.exception.exceptionhandler;

import com.hashedin.eventhub.eventservice.exception.ApplicationException;

import com.hashedin.eventhub.eventservice.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * This class encapsulates the error details when the service encounters such scenario
 *
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ExceptionResponse extends BaseResponse {
    private String errorCode;

    public ExceptionResponse(String errorCode, LocalDateTime timestamp, int status,
                             String message, String path) {
        super(timestamp, status, message, path);
        this.errorCode = errorCode;
    }
}

