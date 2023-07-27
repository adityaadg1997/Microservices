package com.adityagautam.UserService.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String resourceName;
    private String resourceValue;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resource, String resourceName, String resourceValue) {
        super(resource + " with " + resourceName +" "+ resourceValue + " Not Found! ");
        this.resource = resource;
        this.resourceName = resourceName;
        this.resourceValue = resourceValue;
    }
}
