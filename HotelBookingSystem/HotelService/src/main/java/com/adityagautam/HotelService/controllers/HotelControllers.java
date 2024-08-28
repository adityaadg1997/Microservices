package com.adityagautam.HotelService.controllers;

import com.adityagautam.HotelService.payloads.ApiResponse;
import com.adityagautam.HotelService.payloads.HotelDto;
import com.adityagautam.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hotel")
public class HotelControllers {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/")
    public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotelDto){
        HotelDto addedHotel = this.hotelService.addHotel(hotelDto);
        return new ResponseEntity<>(addedHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable String hotelId){
        HotelDto hotel = this.hotelService.getHotel(hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        List<HotelDto> hotelDtos = this.hotelService.getAllHotel();
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable String hotelId, @RequestBody HotelDto hotelDto){
        HotelDto updatedHotel = this.hotelService.updateHotel(hotelId, hotelDto);
        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable String hotelId){
        this.hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>(new ApiResponse("Hotel with hotelId "+ hotelId+ " Successfully deleted!!", true), HttpStatus.OK);
    }

    //get getHotelByLocation
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<HotelDto>> getHotelByLocation(@PathVariable String locationId){
        List<HotelDto> hotelByLocation = this.hotelService.getHotelByLocation(locationId);
        return new ResponseEntity<>(hotelByLocation, HttpStatus.OK);
    }
}
