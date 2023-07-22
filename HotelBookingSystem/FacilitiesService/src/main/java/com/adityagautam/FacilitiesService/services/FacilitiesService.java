package com.adityagautam.FacilitiesService.services;

import com.adityagautam.FacilitiesService.payloads.FacilitiesDto;

import java.util.List;

public interface FacilitiesService {

    //Add Facility
    FacilitiesDto addFacility(FacilitiesDto facilitiesDto);

    //View All Facilities
    List<FacilitiesDto> getAllFacilities();

    //get Facilities by hotel
    List<FacilitiesDto> getFacilitiesByHotel(String hotelId);
}
