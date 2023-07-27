package com.adityagautam.UserService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private String reviewId;
    private String hotelId;
    private String userId;
    private String rating;
    private String reviewDescription;

//    private Hotel hotel;
}
