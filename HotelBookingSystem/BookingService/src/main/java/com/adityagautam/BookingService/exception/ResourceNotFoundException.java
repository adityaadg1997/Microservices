package com.adityagautam.BookingService.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String resourceName;
    private String resourceValue;

    public ResourceNotFoundException(String resource, String resourceName, String resourceValue) {
        super(resource+" with "+resourceName+" : "+resourceValue+ " Not Found !!" );
    }
}
