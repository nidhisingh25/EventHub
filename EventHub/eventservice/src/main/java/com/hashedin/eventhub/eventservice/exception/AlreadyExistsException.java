package com.hashedin.eventhub.eventservice.exception;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(){

    }

    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(String code) {
        super(code);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
