package com.adityagautam.LocationService.services;

import com.adityagautam.LocationService.payloads.LocationDto;

import java.util.List;

public interface LocationService {

    //create location
    LocationDto createLocation(LocationDto locationDto);

    //get location
    LocationDto getLocationById(String locationId);

    //get all location
    List<LocationDto> getAllLocations();

    //update location
    LocationDto updateLocation(String locationId, LocationDto locationDto);

    //delete location
    void deleteLocation(String locationId);
}
