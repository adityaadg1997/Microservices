package com.adityagautam.UserService.external.services;

import com.adityagautam.UserService.entities.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClientService {

    @GetMapping("/api/review/user/{userId}")
    Set<Review> getReviewByUser(@PathVariable String userId);
}
