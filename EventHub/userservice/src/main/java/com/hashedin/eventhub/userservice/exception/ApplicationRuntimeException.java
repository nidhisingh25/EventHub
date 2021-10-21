package com.hashedin.eventhub.userservice.exception;
/**
 * This is the application specific exception thrown when the service
 * encounters any problem in its execution
 *
 */
public class ApplicationRuntimeException extends RuntimeException {

	/**
	 *  Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Please enter code of ExceptionCode as message
     *
     * @param exceptionCode
     */
	public ApplicationRuntimeException(String exceptionCode) {
        super(exceptionCode);
    }
    
    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

