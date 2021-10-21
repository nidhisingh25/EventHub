package com.hashedin.eventhub.eventservice.exception;

public class RecordNotUpdatedException extends ApplicationException{
    public RecordNotUpdatedException(){

    }

    public RecordNotUpdatedException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RecordNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotUpdatedException(String message) {
        super(message);
    }

    public RecordNotUpdatedException(Throwable cause) {
        super(cause);
    }
}
