package com.adityagautam.UserService.payloads;

import com.adityagautam.UserService.entities.Review;
import com.adityagautam.UserService.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private long contact;
    private int age;
    private String address;
    private String city;
    private long pinCode;
    private Set<Review> reviews = new HashSet<>();
    private Set<Role> roles;
}
