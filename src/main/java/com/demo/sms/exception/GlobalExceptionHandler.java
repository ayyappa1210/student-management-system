package com.demo.sms.exception;

import com.demo.sms.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        ErrorResponse error = new ErrorResponse(exception.getMessage(),"EMAIL_EXISTS", LocalDate.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse(exception.getMessage(),"SERVER_ERROR",LocalDate.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(error);
    }

}
