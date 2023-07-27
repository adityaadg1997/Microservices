package com.adityagautam.ReviewService.controllers;

import com.adityagautam.ReviewService.payloads.ReviewDto;
import com.adityagautam.ReviewService.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto){
        ReviewDto review = this.reviewService.createReview(reviewDto);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ReviewDto>> getAllReviews(){
        List<ReviewDto> reviewDtoList = this.reviewService.getAllReviews();
        return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ReviewDto> getReviewByUser(@PathVariable String userId){
        ReviewDto reviewByUser = this.reviewService.getReviewByUser(userId);
        return new ResponseEntity<>(reviewByUser, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ReviewDto> getReviewByHotel(@PathVariable String hotelId){
        ReviewDto reviewByHotel = this.reviewService.getReviewByHotel(hotelId);
        return new ResponseEntity<>(reviewByHotel, HttpStatus.OK);
    }
}
