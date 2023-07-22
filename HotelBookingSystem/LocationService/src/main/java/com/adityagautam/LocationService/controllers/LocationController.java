package com.adityagautam.LocationService.controllers;

import com.adityagautam.LocationService.payloads.ApiResponse;
import com.adityagautam.LocationService.payloads.LocationDto;
import com.adityagautam.LocationService.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto){
        LocationDto location = this.locationService.createLocation(locationDto);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<LocationDto>> getAllLocation(){
        List<LocationDto> allLocations = this.locationService.getAllLocations();
        return new ResponseEntity<>(allLocations, HttpStatus.OK);
    }
    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable String locationId){
        LocationDto location = this.locationService.getLocationById(locationId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

/**

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable String locationId, @RequestBody LocationDto locationDto){
        LocationDto location = this.locationService.updateLocation(locationId, locationDto);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<ApiResponse> deleteLocation(@PathVariable String locationId){
        this.locationService.deleteLocation(locationId);
        return new ResponseEntity<>(new ApiResponse("Location with locationId : "+ locationId + " Successfully deleted !!", true), HttpStatus.OK);
    }
*/
}
