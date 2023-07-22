package com.adityagautam.ReviewService.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String reviewId;
    private String hotelId;
    private String userId;
    private String rating;
    private String reviewDescription;

}
