package com.adityagautam.HotelService.exception;

public class FileNotFoundException extends RuntimeException {

    private String resource;
    private String resourceName;
    private String resourceValue;

    private FileNotFoundException(String resource, String resourceName, String resourceValue) {
        super(resource + " with " + resourceName + " : " + resourceValue + " Not Found !!");
        this.resource = resource;
        this.resourceName = resourceName;
        this.resourceValue = resourceValue;
    }
}
