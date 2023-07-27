package com.adityagautam.UserService.controllers;

import com.adityagautam.UserService.entities.Booking;
import com.adityagautam.UserService.payloads.ApiResponse;
import com.adityagautam.UserService.payloads.UserDto;
import com.adityagautam.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //sign-up new user - with role
    @PostMapping("/register/{userRole}")
    public ResponseEntity<UserDto> signUpNewUser(@RequestBody UserDto userDto, @PathVariable String userRole){
        UserDto signedUpUser = this.userService.signUp(userDto, userRole);
        return new ResponseEntity<>(signedUpUser, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user = this.userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "bookingHotelLocationFacilitiesReviewServiceBreaker", fallbackMethod = "bookingHotelLocationFacilitiesReviewServiceFallBackMethod")
    @Retry(name = "bookingHotelLocationFacilitiesReviewServiceRetry", fallbackMethod = "bookingHotelLocationFacilitiesReviewServiceFallBackMethod")
    @RateLimiter(name = "bookingHotelLocationFacilitiesReviewServiceRateLimiter" , fallbackMethod = "bookingHotelLocationFacilitiesReviewServiceFallBackMethod")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        UserDto user = this.userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //calling fall back for circuit-breaker
    public ResponseEntity<UserDto> bookingHotelLocationFacilitiesReviewServiceFallBackMethod(String userId, Exception exception){
        logger.info("Fall back is executed because some service is down : {}", exception.getMessage());
        UserDto userDto = UserDto.builder().userId("12345").firstName("dummy").lastName("dummy").email("dummy@gmail.com").password("dummyPassword").gender("dummy").contact(1234).age(1).address("dummy").city("dummy").pinCode(12345).build();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtoList = this.userService.getAllUser();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UserDto userDto){
        UserDto updateUser = this.userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("user with userId "+userId+" successfully deleted!", true), HttpStatus.OK);
    }

    //get bookings by hotel
    @GetMapping("/booking/hotel/{hotelId}")
    public ResponseEntity<List<Booking>> getBookingByHotelId(@PathVariable String hotelId){
        List<Booking> bookingByHotelId = this.userService.getBookingByHotelId(hotelId);
        return new ResponseEntity<>(bookingByHotelId, HttpStatus.OK);
    }
}
