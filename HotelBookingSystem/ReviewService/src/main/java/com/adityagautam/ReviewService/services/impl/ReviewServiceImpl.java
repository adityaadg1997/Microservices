package com.adityagautam.ReviewService.services.impl;

import com.adityagautam.ReviewService.entities.Review;
import com.adityagautam.ReviewService.exception.ResourceNotFoundException;
import com.adityagautam.ReviewService.payloads.ReviewDto;
import com.adityagautam.ReviewService.repositories.ReviewRepository;
import com.adityagautam.ReviewService.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = this.modelMapper.map(reviewDto, Review.class);
        String randomId = UUID.randomUUID().toString();
        review.setReviewId(randomId);
        Review createdReview = this.reviewRepository.save(review);
        return this.modelMapper.map(createdReview, ReviewDto.class);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviewList = this.reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = reviewList.stream().map(review -> this.modelMapper.map(review, ReviewDto.class)).collect(Collectors.toList());
        return reviewDtoList;
    }

    @Override
    public ReviewDto getReviewByUser(String userId) {
        Review review = this.reviewRepository.findByUserId(userId);
        ReviewDto reviewDto = this.modelMapper.map(review, ReviewDto.class);
        return reviewDto;
    }

    @Override
    public ReviewDto getReviewByHotel(String hotelId) {
        Review review = this.reviewRepository.findByHotelId(hotelId);
        ReviewDto reviewDto = this.modelMapper.map(review, ReviewDto.class);
        return reviewDto;
    }
}
