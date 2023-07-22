package com.adityagautam.HotelService.services;

import com.adityagautam.HotelService.payloads.HotelDto;

import java.util.List;

public interface HotelService {

    //create
    HotelDto addHotel(HotelDto hotelDto);

    //read
    HotelDto getHotel(String hotelId);

    //read all
    List<HotelDto> getAllHotel();

    //update
    HotelDto updateHotel(String hotelId,HotelDto hotelDto);

    //delete
    void deleteHotel(String hotelId);

    //get Hotels By Location
    List<HotelDto> getHotelByLocation(String locationId);

}
