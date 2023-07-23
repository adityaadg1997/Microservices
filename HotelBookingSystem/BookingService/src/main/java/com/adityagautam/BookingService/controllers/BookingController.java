package com.adityagautam.BookingService.controllers;

import com.adityagautam.BookingService.payloads.BookingDto;
import com.adityagautam.BookingService.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //create booking
    @PostMapping("/")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto){
        BookingDto booking = this.bookingService.createBooking(bookingDto);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    //update booking status
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable String bookingId, @RequestBody BookingDto bookingDto){
        BookingDto updatedBookingStatus = this.bookingService.updateBookingStatus(bookingId, bookingDto);
        return new ResponseEntity<>(updatedBookingStatus, HttpStatus.OK);
    }

    //get all bookings
    @GetMapping("/")
    public ResponseEntity<List<BookingDto>> getAllBookings(){
        List<BookingDto> allBookings = this.bookingService.getAllBookings();
        return new ResponseEntity<>(allBookings, HttpStatus.OK);
    }

    //get booking by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getBookingByUserId(@PathVariable String userId){
        List<BookingDto> bookingByUserId = this.bookingService.getBookingByUserId(userId);
        return new ResponseEntity<>(bookingByUserId, HttpStatus.OK);
    }

    //get booking by hotelId
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<BookingDto>> getBookingByHotelId(@PathVariable String hotelId){
        List<BookingDto> bookingByHotelId = this.bookingService.getBookingByHotelId(hotelId);
        return new ResponseEntity<>(bookingByHotelId, HttpStatus.OK);
    }
}
