package com.adityagautam.ReviewService.services;

import com.adityagautam.ReviewService.payloads.ReviewDto;

import java.util.List;

public interface ReviewService {

    //create Review
    ReviewDto createReview(ReviewDto reviewDto);

    //getAllReviews
    List<ReviewDto> getAllReviews();

    // get single Review by User
    ReviewDto getReviewByUser(String userId);

    // get single reviews by Hotel.
    ReviewDto getReviewByHotel(String hotelId);

}
