package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Facilities;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "FACILITIES-SERVICE")
public interface FacilitiesClientService {

    @GetMapping("/api/facilities/hotel/{hotelId}")
    List<Facilities> getFacilitiesByHotel(@PathVariable String hotelId);
}
