package com.adityagautam.HotelService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facilities {

    private String facilityId;
    private String hotelId;
    private String facilityName;
    private String facilityDescription;
}
