package com.adityagautam.UserService.services.impl;

import com.adityagautam.UserService.constants.AppConstants;
import com.adityagautam.UserService.entities.*;
import com.adityagautam.UserService.exception.ResourceNotFoundException;
import com.adityagautam.UserService.external.services.FacilitiesClientService;
import com.adityagautam.UserService.external.services.HotelClientService;
import com.adityagautam.UserService.external.services.LocationClientService;
import com.adityagautam.UserService.external.services.ReviewClientService;
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

import java.util.List;
import java.util.Set;
import java.util.UUID;
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

        user.getRoles().add(role);
        logger.info("user.getRoles().add(role) - {} ", user.getRoles().add(role));

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
       /**calling REVIEW-SERVICE to fetch user review from userId*/
        Set<Review> reviewByUser = this.reviewClientService.getReviewByUser(user.getUserId());
        logger.info("reviewByUser : {}", reviewByUser);


        /**Calling HOTEL-SERVICE to fetch hotel from review*/
        Set<Review> reviewList = reviewByUser.stream().map(review -> {
            Hotel hotelByIdFromReview = this.hotelClientService.getHotelById(review.getHotelId());
            review.setHotel(hotelByIdFromReview);
        /**Calling FACILITIES-SERVICE to fetch facilities from hotel*/
            Set<Facilities> facilitiesByHotel = this.facilitiesClientService.getFacilitiesByHotel(review.getHotelId());
            review.getHotel().setFacilities(facilitiesByHotel);

        /**Calling LOCATION-SERVICE to fetch location from hotel*/
            Location locationById = this.locationClientService.getLocationById(review.getHotel().getLocationId());
            review.getHotel().setLocation(locationById);
            return review;
        }).collect(Collectors.toSet());

        user.setReviews(reviewList);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
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
}
