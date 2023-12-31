package com.adityagautam.ReviewService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @Id
    private String reviewId;
    private String hotelId;
    private String userId;
    private String rating;
    private String reviewDescription;

}
