package com.adityagautam.HotelService.exception;

public class HotelBaseException extends RuntimeException {

    public HotelBaseException(String message) {
        super(message);
    }

    public HotelBaseException() {
        super("Exception occurred in Hotel Service");
    }
}
