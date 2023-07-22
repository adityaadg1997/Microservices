package com.adityagautam.ReviewService.services;

import com.adityagautam.ReviewService.payloads.ReviewDto;

import java.util.List;

public interface ReviewService {

    //getAllReviews
    List<ReviewDto> getAllReviews();

    //create Review
    ReviewDto createReview(ReviewDto reviewDto);

    // get Review by User
    List<ReviewDto> getReviewByUser(String userId);

    // View Hotel Reviews.
    List<ReviewDto> getReviewByHotel(String hotelId);

}
