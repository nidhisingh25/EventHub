package com.hashedin.eventhub.eventservice.exception;

public class FileStorageException extends ApplicationException {
    public FileStorageException(){

    }

    public FileStorageException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }
}
