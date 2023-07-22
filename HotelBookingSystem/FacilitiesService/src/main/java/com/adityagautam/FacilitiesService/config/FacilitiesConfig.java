package com.adityagautam.FacilitiesService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacilitiesConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
