package com.adityagautam.FacilitiesService.repositories;

import com.adityagautam.FacilitiesService.entities.Facilities;
import com.adityagautam.FacilitiesService.payloads.FacilitiesDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilitiesRepositories extends JpaRepository<Facilities, String> {

    //custom finder method
    List<Facilities> findByHotelId(String hotelId);
}
