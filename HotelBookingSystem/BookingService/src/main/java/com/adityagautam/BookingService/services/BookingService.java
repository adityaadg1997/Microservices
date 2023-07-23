package com.adityagautam.BookingService.services;

import com.adityagautam.BookingService.payloads.BookingDto;

import java.util.List;

public interface BookingService {

    //create booking
    BookingDto createBooking(BookingDto bookingDto);

    //update booking status
    BookingDto updateBookingStatus(String bookingId, BookingDto bookingDto);

    //get all bookings
    List<BookingDto> getAllBookings();

    //get booking by userId
    List<BookingDto> getBookingByUserId(String userId);

    //get booking by hotelId
    List<BookingDto> getBookingByHotelId(String hotelId);


}
