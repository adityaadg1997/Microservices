package com.adityagautam.BookingService.services;

import com.razorpay.RazorpayException;
import java.util.Map;

public interface PaymentService {

    String createOrder(Map<String, Object> paymentRequest) throws RazorpayException;

}
