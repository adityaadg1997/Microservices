package com.adityagautam.UserService.payloads;

import com.adityagautam.UserService.entities.Booking;
import com.adityagautam.UserService.entities.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<Booking> bookings = new ArrayList<>();
    private Set<Role> roles;
}
