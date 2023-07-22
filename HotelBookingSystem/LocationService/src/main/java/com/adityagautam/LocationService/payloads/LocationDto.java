package com.adityagautam.LocationService.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private String locationId;
    private String location;
    private String locationDescription;
}
