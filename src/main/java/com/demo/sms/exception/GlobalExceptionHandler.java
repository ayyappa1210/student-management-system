package com.demo.sms.exception;

import com.demo.sms.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest webRequest) {
        logger.warn("Conflict detected: {}", exception.getMessage());
        ErrorResponse error = new ErrorResponse(exception.getMessage(),"EMAIL_EXISTS",webRequest.getDescription(false) ,LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException exception, WebRequest webRequest) {
        ErrorResponse response =new ErrorResponse(exception.getMessage(),"NOT_FOUND",webRequest.getDescription(false),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception,WebRequest webRequest) {
        logger.error("An unexpected runtime error occurred: {}", exception.getMessage(), exception);
        ErrorResponse error = new ErrorResponse(exception.getMessage(),"SERVER_ERROR",webRequest.getDescription(false), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,WebRequest webRequest) {
        logger.error("An unhandled exception occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),"GLOBAL_ERROR",webRequest.getDescription(false),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

    }

}
