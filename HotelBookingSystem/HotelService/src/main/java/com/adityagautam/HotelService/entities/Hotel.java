package com.adityagautam.HotelService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Hotel {

    @Id
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

    @Transient
    private Set<Facilities> facilities = new HashSet<>();
    @Transient
    private Set<Review> reviews = new HashSet<>();


}
