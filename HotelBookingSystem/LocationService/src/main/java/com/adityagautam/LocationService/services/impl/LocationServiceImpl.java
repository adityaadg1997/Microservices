package com.adityagautam.LocationService.services.impl;

import com.adityagautam.LocationService.entities.Location;
import com.adityagautam.LocationService.exception.ResourceNotFoundException;
import com.adityagautam.LocationService.payloads.LocationDto;
import com.adityagautam.LocationService.repositories.LocationRepository;
import com.adityagautam.LocationService.services.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = this.modelMapper.map(locationDto, Location.class);
        String randomId = UUID.randomUUID().toString();
        location.setLocationId(randomId);

        Location createdLocation = this.locationRepository.save(location);
        return this.modelMapper.map(createdLocation, LocationDto.class);
    }

    @Override
    public LocationDto getLocationById(String locationId) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Location", "locationId", locationId));
        return this.modelMapper.map(location, LocationDto.class);
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locationList = this.locationRepository.findAll();
        List<LocationDto> locationDtos = locationList.stream().map(location -> this.modelMapper.map(location, LocationDto.class)).collect(Collectors.toList());
        return locationDtos;
    }

    @Override
    public LocationDto updateLocation(String locationId, LocationDto locationDto) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Location", "locationId", locationId));
        location.setLocation(locationDto.getLocation());
        location.setLocationDescription(locationDto.getLocationDescription());

        Location updatedLocation = this.locationRepository.save(location);
        return this.modelMapper.map(updatedLocation, LocationDto.class);
    }

    @Override
    public void deleteLocation(String locationId) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Location", "locationId", locationId));
        this.locationRepository.delete(location);
    }
}
