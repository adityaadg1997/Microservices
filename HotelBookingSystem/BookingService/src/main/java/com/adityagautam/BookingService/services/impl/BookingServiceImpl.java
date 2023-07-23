package com.adityagautam.BookingService.services.impl;

import com.adityagautam.BookingService.entities.Booking;
import com.adityagautam.BookingService.exception.ResourceNotFoundException;
import com.adityagautam.BookingService.payloads.BookingDto;
import com.adityagautam.BookingService.repositories.BookingRepository;
import com.adityagautam.BookingService.services.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = this.modelMapper.map(bookingDto, Booking.class);
        String randomId = UUID.randomUUID().toString();
        booking.setBookingId(randomId);
        booking.setBookingStatus(false);
        Booking bookingCreated = this.bookingRepository.save(booking);
        return this.modelMapper.map(bookingCreated, BookingDto.class);
    }

    @Override
    public BookingDto updateBookingStatus(String bookingId, BookingDto bookingDto) {
        Booking booking = this.bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));
        booking.setBookingStatus(bookingDto.isBookingStatus());
        Booking updatedBooking = this.bookingRepository.save(booking);
        return this.modelMapper.map(updatedBooking, BookingDto.class);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookingList = this.bookingRepository.findAll();
        List<BookingDto> bookingDtoList = bookingList.stream().map(booking -> this.modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> getBookingByUserId(String userId) {
        List<Booking> bookingList = this.bookingRepository.findByUserId(userId);
        List<BookingDto> bookingDtoList = bookingList.stream().map(booking -> this.modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
        return bookingDtoList;
    }

    @Override
    public List<BookingDto> getBookingByHotelId(String hotelId) {
        List<Booking> bookingList = this.bookingRepository.findByHotelId(hotelId);
        List<BookingDto> bookingDtoList = bookingList.stream().map(booking -> this.modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
        return bookingDtoList;
    }
}
