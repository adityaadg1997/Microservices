package com.adityagautam.ReviewService.services;

import com.adityagautam.ReviewService.payloads.ReviewDto;

import java.util.List;

public interface ReviewService {

    //create Review
    ReviewDto createReview(ReviewDto reviewDto);

    //getAllReviews
    List<ReviewDto> getAllReviews();

    // get Review by User
    List<ReviewDto> getReviewByUser(String userId);

    // View Hotel Reviews.
    List<ReviewDto> getReviewByHotel(String hotelId);

}
