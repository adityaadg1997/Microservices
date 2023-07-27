package com.adityagautam.UserService.services.impl;

import com.adityagautam.UserService.constants.AppConstants;
import com.adityagautam.UserService.entities.*;
import com.adityagautam.UserService.exception.ResourceNotFoundException;
import com.adityagautam.UserService.external.services.*;
import com.adityagautam.UserService.payloads.UserDto;
import com.adityagautam.UserService.repositories.RoleRepository;
import com.adityagautam.UserService.repositories.UserRepository;
import com.adityagautam.UserService.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReviewClientService reviewClientService;

    @Autowired
    private HotelClientService hotelClientService;

    @Autowired
    private FacilitiesClientService facilitiesClientService;

    @Autowired
    private LocationClientService locationClientService;

    @Autowired
    private BookingClientService bookingClientService;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto signUp(UserDto userDto, String userRole) {
        User user = this.modelMapper.map(userDto, User.class);
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role;
        /**assign role to new user -
         * 1. get role by role_id(ROLE_CUSTOMER_ID), in role repo ?
         * 2. then add the role in user
         * 3. then save the user*/
        if (userRole.equals("admin")) {
            role = this.roleRepository.findById(AppConstants.ROLE_ADMIN_ID).get();
        }else if (userRole.equals("manager")){
            role = this.roleRepository.findById(AppConstants.ROLE_MANAGER_ID).get();
        }else {
            role = this.roleRepository.findById(AppConstants.ROLE_CUSTOMER_ID).get();
        }

        Set<Role>  roles =new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        logger.info("user.getRoles().add(role) - {} ", user.getRoles());

        User newUser = this.userRepository.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public UserDto getUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        /**Calling BOOKING-SERVICE to fetch booking from userId*/
        List<Booking> bookingByUserId = this.bookingClientService.getBookingByUserId(user.getUserId());

        /**Calling HOTEL-SERVICE to fetch hotel from bookingId*/
        List<Booking> bookings = bookingByUserId.stream().map(booking -> {
            Hotel hotel = this.hotelClientService.getHotelById(booking.getHotelId());
            booking.setHotel(hotel);
        /**Calling LOCATION-SERVICE to fetch location from hotel-locationId*/
            Location location = this.locationClientService.getLocationById(hotel.getLocationId());
            booking.getHotel().setLocation(location);
        /**Calling FACILITIES-SERVICE to fetch facility from hotelId*/
            List<Facilities> facilities = this.facilitiesClientService.getFacilitiesByHotel(hotel.getHotelId());
            booking.getHotel().setFacilities(facilities);
        /**Calling REVIEW-SERVICE to fetch reviews from hotelId*/
            Review review = this.reviewClientService.getReviewByHotelId(hotel.getHotelId());
            booking.getHotel().setReview(review);
            return booking;
        }).collect(Collectors.toList());

        user.setBookings(bookings);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.userRepository.findAll();

        List<User> users = userList.stream().map(user -> {
            this.getUser(user.getUserId());
            return user;
        }).collect(Collectors.toList());

        List<UserDto> userDtoList = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setGender(userDto.getGender());
        user.setContact(userDto.getContact());
        user.setAge(userDto.getAge());
        user.setAddress(userDto.getAddress());
        user.setCity(userDto.getCity());
        user.setPinCode(userDto.getPinCode());

        User updatedUser = this.userRepository.save(user);
        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        this.userRepository.delete(user);
    }

    @Override
    public List<Booking> getBookingByHotelId(String hotelId) {
        List<Booking> bookingList = this.bookingClientService.getBookingByHotelId(hotelId);
        List<Booking> bookings = bookingList.stream().map(booking -> {
            Hotel hotel = this.hotelClientService.getHotelById(booking.getHotelId());
            booking.setHotel(hotel);
            Location location = this.locationClientService.getLocationById(hotel.getLocationId());
            booking.getHotel().setLocation(location);
            List<Facilities> facilities = this.facilitiesClientService.getFacilitiesByHotel(booking.getHotelId());
            booking.getHotel().setFacilities(facilities);
            Review review = this.reviewClientService.getReviewByHotelId(booking.getHotelId());
            booking.getHotel().setReview(review);
            return booking;
        }).collect(Collectors.toList());

        return bookings;
    }
}
