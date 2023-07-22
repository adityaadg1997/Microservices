package com.adityagautam.FacilitiesService.services.impl;

import com.adityagautam.FacilitiesService.entities.Facilities;
import com.adityagautam.FacilitiesService.payloads.FacilitiesDto;
import com.adityagautam.FacilitiesService.repositories.FacilitiesRepositories;
import com.adityagautam.FacilitiesService.services.FacilitiesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FacilitiesServiceImpl implements FacilitiesService {

    @Autowired
    private FacilitiesRepositories facilitiesRepositories;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FacilitiesDto addFacility(FacilitiesDto facilitiesDto) {
        Facilities facilities = this.modelMapper.map(facilitiesDto, Facilities.class);
        String randomId = UUID.randomUUID().toString();
        facilities.setFacilityId(randomId);
        Facilities savedFacilities = this.facilitiesRepositories.save(facilities);
        return this.modelMapper.map(savedFacilities, FacilitiesDto.class);
    }

    @Override
    public List<FacilitiesDto> getAllFacilities() {
        List<Facilities> facilitiesList = this.facilitiesRepositories.findAll();
        List<FacilitiesDto> facilitiesDtos = facilitiesList.stream().map(facilities -> this.modelMapper.map(facilities, FacilitiesDto.class)).collect(Collectors.toList());
        return facilitiesDtos;
    }

    @Override
    public List<FacilitiesDto> getFacilitiesByHotel(String hotelId) {
        List<Facilities> facilitiesList = this.facilitiesRepositories.findByHotelId(hotelId);
        List<FacilitiesDto> facilitiesDtos = facilitiesList.stream().map(facilities -> this.modelMapper.map(facilities, FacilitiesDto.class)).collect(Collectors.toList());
        return facilitiesDtos;
    }
}
