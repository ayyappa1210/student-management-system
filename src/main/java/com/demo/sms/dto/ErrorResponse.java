package com.demo.sms.dto;

import java.time.LocalDate;

public class ErrorResponse {

    private String message;
    private String errorCode;
    private LocalDate timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String errorCode, LocalDate timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
