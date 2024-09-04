package com.adityagautam.BookingService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class RCOrder {

    @Id
    private String id;
    private String orderId;
    private String paymentId;
    private int amount;
    private int amountPaid;
    private int amountDue;
    private String receipt;
    private Date createdAt;
    private String status;
    private String signature;

}
