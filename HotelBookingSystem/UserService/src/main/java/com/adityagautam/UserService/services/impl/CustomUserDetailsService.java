package com.adityagautam.UserService.services.impl;

import com.adityagautam.UserService.entities.User;
import com.adityagautam.UserService.exception.ResourceNotFoundException;
import com.adityagautam.UserService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load data from DB
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return user;
    }
}
