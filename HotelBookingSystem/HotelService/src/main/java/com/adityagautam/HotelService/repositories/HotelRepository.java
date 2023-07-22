package com.adityagautam.HotelService.repositories;

import com.adityagautam.HotelService.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    //custom finder method
    List<Hotel> findByLocationId(String locationId);
}
