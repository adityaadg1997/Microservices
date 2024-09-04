package com.adityagautam.BookingService.repositories;

import com.adityagautam.BookingService.entities.RCOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<RCOrder, String> {

    RCOrder findByOrderId(String orderId);
}
