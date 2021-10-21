package com.hashedin.eventhub.eventservice.exception.exceptionhandler;

import static java.lang.String.format;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;


import com.hashedin.eventhub.eventservice.exception.*;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This is the global exception handler.
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles scenario - if no handler is found for the requested resource
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String requestURL = ((ServletWebRequest)request).getRequest().getRequestURL().toString();
        String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
        return errorResponse(ex, ExceptionCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, requestURL, requestURI);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.DB_OPERATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.CONSTRAINT_VIOLATION_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    @ExceptionHandler(value = RecordNotAddedException.class)
    public ResponseEntity<Object> handleApplicationRuntimeException(RecordNotAddedException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.getExceptionDetailsByCode(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> handleApplicationRuntimeException(RecordNotFoundException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.getExceptionDetailsByCode(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }
    @ExceptionHandler(value = RecordNotUpdatedException.class)
    public ResponseEntity<Object> handleApplicationRuntimeException(RecordNotUpdatedException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.getExceptionDetailsByCode(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Object> handleApplicationRuntimeException(ApplicationException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.getExceptionDetailsByCode(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyEnrolledException(AlreadyExistsException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        return errorResponse(ex, ExceptionCode.getExceptionDetailsByCode(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, requestURL, requestURI);
    }
    /**
     * @param ex
     * @param exceptionCode
     * @param httpStatus
     * @return
     */
    public ResponseEntity<Object> errorResponse(Exception ex, ExceptionCode exceptionCode,
                                                HttpStatus httpStatus, String requestURL, String requestURI) {

        String message=exceptionCode.formatMessage()?
                format(exceptionCode.getMessage(), requestURL):exceptionCode.getMessage();

        ExceptionResponse exResp = new ExceptionResponse(exceptionCode.getCode(), LocalDateTime.now(),
                httpStatus.value(), message, requestURI);

        String exceptionMessage = exResp.toString();
        LOG.error(exceptionMessage, ex);

        return new ResponseEntity<>(exResp, httpStatus);
    }
}

