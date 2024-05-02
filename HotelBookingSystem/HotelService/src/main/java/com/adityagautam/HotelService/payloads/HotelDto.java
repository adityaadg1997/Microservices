package com.adityagautam.HotelService.payloads;

//import com.adityagautam.HotelService.entities.Facilities;
//import com.adityagautam.HotelService.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    private String hotelId;
    private String locationId;
    private String hotelName;
    private String hotelDescription;
    private String hotelManager;
    private String email;
    private Integer pricePerDay;
    private Integer totalRooms;
    private String address;
    private long pinCode;
    private List<String> hotelImages;
}
