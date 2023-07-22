package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "LOCATION-SERVICE")
public interface LocationClientService {

    @GetMapping("api/location/{locationId}")
    Location getLocationById(@PathVariable String locationId);
}
