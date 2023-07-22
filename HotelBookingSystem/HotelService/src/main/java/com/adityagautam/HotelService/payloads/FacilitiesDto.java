package com.adityagautam.HotelService.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilitiesDto {

    private String facilityId;
    private String hotelId;
    private String facilityName;
    private String facilityDescription;
}
