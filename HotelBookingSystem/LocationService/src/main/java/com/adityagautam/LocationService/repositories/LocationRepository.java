package com.adityagautam.LocationService.repositories;

import com.adityagautam.LocationService.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
