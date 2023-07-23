package com.adityagautam.BookingService.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String bookingId;
    private String hotelId;
    private String userId;
    private Date checkIn;
    private Date checkOut;
    private Integer totalRoom;
    private Integer totalDays;
    private boolean bookingStatus;
}
