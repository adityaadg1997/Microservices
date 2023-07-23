package com.adityagautam.BookingService.repositories;

import com.adityagautam.BookingService.entities.Booking;
import com.adityagautam.BookingService.payloads.BookingDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    //custom finder method
    List<Booking> findByUserId(String userId);

    List<Booking> findByHotelId(String hotelId);
}
