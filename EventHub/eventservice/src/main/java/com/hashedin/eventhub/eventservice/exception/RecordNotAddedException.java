package com.hashedin.eventhub.eventservice.exception;

public class RecordNotAddedException extends ApplicationException{

    public RecordNotAddedException(){

    }

    public RecordNotAddedException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RecordNotAddedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotAddedException(String message) {
        super(message);
    }

    public RecordNotAddedException(Throwable cause) {
        super(cause);
    }
}
