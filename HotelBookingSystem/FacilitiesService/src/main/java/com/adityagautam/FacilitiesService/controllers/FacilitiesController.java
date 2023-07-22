package com.adityagautam.FacilitiesService.controllers;

import com.adityagautam.FacilitiesService.payloads.FacilitiesDto;
import com.adityagautam.FacilitiesService.services.FacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/facilities")
public class FacilitiesController {

    @Autowired
    private FacilitiesService facilitiesService;

    @PostMapping("/")
    public ResponseEntity<FacilitiesDto> createFacility(@RequestBody FacilitiesDto facilitiesDto){
        FacilitiesDto addedFacility = this.facilitiesService.addFacility(facilitiesDto);
        return new ResponseEntity<>(addedFacility, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<FacilitiesDto>> getAllFacilities(){
        List<FacilitiesDto> allFacilities = this.facilitiesService.getAllFacilities();
        return new ResponseEntity<>(allFacilities, HttpStatus.OK);
    }

    //get facility by hotel
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<FacilitiesDto>> getFacilitiesByHotel(@PathVariable String hotelId){
        List<FacilitiesDto> facilitiesByHotel = this.facilitiesService.getFacilitiesByHotel(hotelId);
        return new ResponseEntity<>(facilitiesByHotel, HttpStatus.OK);
    }
}
