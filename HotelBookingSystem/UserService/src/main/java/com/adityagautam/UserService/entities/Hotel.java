package com.adityagautam.UserService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private String hotelId;
    private String locationId;
    private String hotelName;
    //    private String hotelLocation;
    private String hotelDescription;
    private String hotelManager;
    private String email;
    private Integer pricePerDay;
    private Integer totalRooms;
    private String address;
    private long pinCode;
    private String hotelImages;

    private Location location;
    private List<Facilities> facilities = new ArrayList<>();
    private Review review;
}
