package com.ssafy.fitpass.exception;

public class UserException extends RuntimeException{

    private final String errorCode;

    public UserException(String message, String errorCode) { // errorCode =  NF / TC
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
