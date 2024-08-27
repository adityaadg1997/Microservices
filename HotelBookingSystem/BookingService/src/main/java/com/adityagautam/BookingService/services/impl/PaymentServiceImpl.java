package com.adityagautam.BookingService.services.impl;

import com.adityagautam.BookingService.services.PaymentService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String KEY_ID = "rzp_test_K1BP3qb1cpaxw6";
    private static final String KEY_SECRET = "5PF0Aed50EaxMmgUAfaKse3A";
    private static final String CURRENCY = "INR";


    @Override
    public String createOrder(Map<String, Object> paymentRequest) throws RazorpayException {
        int amount = Integer.parseInt(paymentRequest.get("amount").toString());

        RazorpayClient razorpayClient = new RazorpayClient(KEY_ID, KEY_SECRET);
        JSONObject orderRequest = getOrderRequest(amount);

        //before returning You can save the order data in your DB for better practice.
        return razorpayClient.orders.create(orderRequest).toString();
    }

    private JSONObject getOrderRequest(int amount) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", CURRENCY);
        orderRequest.put("receipt", "receipt#1");
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        orderRequest.put("notes", notes);
        return orderRequest;
    }
}
