package com.adityagautam.UserService.services;

import com.adityagautam.UserService.entities.Booking;
import com.adityagautam.UserService.payloads.UserDto;

import java.util.List;

public interface UserService {

    //signUp
    UserDto signUp(UserDto userDto, String userRole);

    //create
    UserDto createUser(UserDto userDto);

    //read
    UserDto getUser(String userId);

    //read all
    List<UserDto> getAllUser();

    //update
    UserDto updateUser(String userId, UserDto userDto);

    //delete
    void deleteUser(String userId);

    // getBookingByHotelId
    List<Booking> getBookingByHotelId(String hotelId);
}
