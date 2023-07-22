package com.adityagautam.ReviewService.repositories;

import com.adityagautam.ReviewService.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    //custom finder method
    List<Review> findByUserId(String userId);

    List<Review> findByHotelId(String hotelId);
}
