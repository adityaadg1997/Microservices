package com.adityagautam.BookingService.services.impl;

import com.adityagautam.BookingService.entities.RCOrder;
import com.adityagautam.BookingService.repositories.OrderRepository;
import com.adityagautam.BookingService.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepo;

    private static final String KEY_ID = "rzp_test_K1BP3qb1cpaxw6";
    private static final String KEY_SECRET = "5PF0Aed50EaxMmgUAfaKse3A";
    private static final String CURRENCY = "INR";


    @Override
    public String createOrder(Map<String, Object> paymentRequest) throws RazorpayException {
        int amount = Integer.parseInt(paymentRequest.get("amount").toString());

        RazorpayClient razorpayClient = new RazorpayClient(KEY_ID, KEY_SECRET);
        JSONObject orderRequest = getOrderRequest(amount);

        //before returning You can save the order data in your DB for better practice.
        Order order = razorpayClient.orders.create(orderRequest);

        //save order to DB
        RCOrder rcOrder = getRoomsCornerOrder(order);
        this.orderRepo.save(rcOrder);

        return order.toString();
    }

    @Override
    public void updateOrder(Map<String, Object> paymentRequest) throws RazorpayException {
        RCOrder rcOrder = orderRepo.findByOrderId(paymentRequest.get("razorpay_order_id").toString());

        rcOrder.setPaymentId(paymentRequest.get("razorpay_payment_id").toString());
        rcOrder.setStatus(paymentRequest.get("status").toString());
        rcOrder.setSignature(paymentRequest.get("razorpay_signature").toString());

        orderRepo.save(rcOrder);
    }

    private JSONObject getOrderRequest(int amount) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", CURRENCY);
        orderRequest.put("receipt", "receipt#1"); // receipt value can be dynamically set
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        orderRequest.put("notes", notes);
        return orderRequest;
    }

    private RCOrder getRoomsCornerOrder(Order order) {
        RCOrder rcOrder = new RCOrder();
        String randomId = UUID.randomUUID().toString();
        rcOrder.setId(randomId);
        rcOrder.setOrderId(order.get("id"));
        rcOrder.setPaymentId(null);
        rcOrder.setAmount(order.get("amount"));
        rcOrder.setAmountPaid(order.get("amount_paid"));
        rcOrder.setAmountDue(order.get("amount_due"));
        rcOrder.setReceipt(order.get("receipt"));
        rcOrder.setCreatedAt(order.get("created_at"));
        rcOrder.setStatus(order.get("status"));
        return rcOrder;
    }
}
