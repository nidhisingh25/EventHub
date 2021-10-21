package com.hashedin.eventhub.userservice.exception.handler;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ExceptionCode {

	INVALID_CREDENTIALS("RST-00001", "Invalid Credentials Provided"),

	UNAUTHORIZED_URL("RST-00002", "Invalid authorization for accessing URL [%s]", true),

	TOKEN_EXPIRED("RST-00003", "JWT Token Expired"),

	RESOURCE_NOT_FOUND("RST-00101", "Resource [%s] Not Found", true),
	
	DB_OPERATION_ERROR("RST-00102", "Database Operation Error Occurred"),
	
	INTERNAL_SERVER_ERROR("RST-00103", "Internal Server Error Occurred"),
	
	CONSTRAINT_VIOLATION_ERROR("RST-00104", "Constraint Violation Occurred"),

	EMAIL_VALIDATION_ERROR("RST-00105", "Given Email Address is invalid"),

	USER_NOT_FOUND_BY_EMAIL("RST-00106", "User Not Found for the given Email"),

	USER_NOT_FOUND_BY_ID("RST-00107", "User Not Found for the given Id"),

	EMAIL_ALREADY_IN_USE("RST-00108", "Given Email address is already in use");

	private String code;

	private String message;
	
	private boolean formatMessage;

	ExceptionCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	ExceptionCode(String code, String message, boolean formatMessage) {
		this(code, message);
		this.formatMessage = formatMessage;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the formatMessage
	 */
	public boolean formatMessage() {
		return formatMessage;
	}

	public static ExceptionCode getExceptionDetailsByCode(String code) {
		return Arrays
				.stream(ExceptionCode.values())
				.filter(value -> StringUtils.equals(value.getCode(),code))
				.collect(Collectors.toList())
				.get(0);
	}
}
