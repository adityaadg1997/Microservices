package com.adityagautam.BookingService.controllers;

import com.adityagautam.BookingService.services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/payment")
public class PaymentApi {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create/order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> paymentRequest) throws RazorpayException {
        return new ResponseEntity<>(paymentService.createOrder(paymentRequest), HttpStatus.OK);
    }

    @PostMapping("/update/order")
    public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> paymentData) throws RazorpayException {
        paymentService.updateOrder(paymentData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
