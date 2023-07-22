package com.adityagautam.UserService.controllers;

import com.adityagautam.UserService.payloads.ApiResponse;
import com.adityagautam.UserService.payloads.UserDto;
import com.adityagautam.UserService.services.UserService;
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
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        UserDto user = this.userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
}
