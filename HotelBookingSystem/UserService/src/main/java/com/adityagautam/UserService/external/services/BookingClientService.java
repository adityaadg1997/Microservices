package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Booking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("BOOKING-SERVICE")
public interface BookingClientService {

    @GetMapping("/api/booking/user/{userId}")
    List<Booking> getBookingByUserId(@PathVariable String userId);

    @GetMapping("/api/booking/hotel/{hotelId}")
    List<Booking> getBookingByHotelId(@PathVariable String hotelId);
}
