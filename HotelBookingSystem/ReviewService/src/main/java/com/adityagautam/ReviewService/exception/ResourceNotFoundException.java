package com.adityagautam.ReviewService.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String resourceName;
    private String resourceValue;

    public ResourceNotFoundException(String resource, String resourceName, String resourceValue) {
        super(resource+" Not Found with "+resourceName+ " : "+resourceValue);
        this.resource=resource;
        this.resourceName=resourceName;
        this.resourceValue=resourceValue;
    }
}
