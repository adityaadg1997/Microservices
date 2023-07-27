package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClientService {

    @GetMapping("/api/review/hotel/{hotelId}")
    Review getReviewByHotelId(@PathVariable String hotelId);
}
