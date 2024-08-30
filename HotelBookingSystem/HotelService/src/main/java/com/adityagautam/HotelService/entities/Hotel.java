package com.adityagautam.HotelService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Hotel {

    @Id
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
    @Column(length = 1000)
    private List<String> hotelImages;
}
