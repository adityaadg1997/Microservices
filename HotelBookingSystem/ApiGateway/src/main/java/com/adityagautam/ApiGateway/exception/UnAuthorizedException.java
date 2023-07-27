package com.adityagautam.ApiGateway.exception;

public class UnAuthorizedException extends RuntimeException {
    private String message;

    public UnAuthorizedException(String message) {
        super(message);
        this.message=message;
    }
}
