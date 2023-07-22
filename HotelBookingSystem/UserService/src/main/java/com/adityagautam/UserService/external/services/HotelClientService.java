package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelClientService {

    @GetMapping("/api/hotel/{hotelId}")
    Hotel getHotelById(@PathVariable String hotelId);
}
