package com.adityagautam.ApiGateway.exception;

public class HeaderNotFoundException extends RuntimeException{

    public HeaderNotFoundException(String message) {
        super(message);
    }
}
