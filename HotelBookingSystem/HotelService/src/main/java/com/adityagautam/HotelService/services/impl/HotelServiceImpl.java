package com.adityagautam.HotelService.services.impl;

import com.adityagautam.HotelService.entities.Hotel;
import com.adityagautam.HotelService.exception.ResourceNotFoundException;
import com.adityagautam.HotelService.payloads.HotelDto;
import com.adityagautam.HotelService.repositories.HotelRepository;
import com.adityagautam.HotelService.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public HotelDto addHotel(HotelDto hotelDto) {
        Hotel hotel = this.modelMapper.map(hotelDto, Hotel.class);
        String randomId = UUID.randomUUID().toString();
        hotel.setHotelId(randomId);
        Hotel createdHotel = this.hotelRepository.save(hotel);
        return this.modelMapper.map(createdHotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotel(String hotelId) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
        return this.modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public List<HotelDto> getAllHotel() {
        List<Hotel> hotelList = this.hotelRepository.findAll();
        List<HotelDto> hotelDtos = hotelList.stream().map(hotel -> this.modelMapper.map(hotel, HotelDto.class)).collect(Collectors.toList());
        return hotelDtos;
    }

    @Override
    public HotelDto updateHotel(String hotelId, HotelDto hotelDto) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
        hotel.setHotelName(hotelDto.getHotelName());
//        hotel.setHotelLocation(hotelDto.getHotelLocation());
        hotel.setHotelDescription(hotelDto.getHotelDescription());
        hotel.setHotelManager(hotelDto.getHotelManager());
        hotel.setEmail(hotelDto.getEmail());
        hotel.setPricePerDay(hotelDto.getPricePerDay());
        hotel.setTotalRooms(hotelDto.getTotalRooms());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setPinCode(hotelDto.getPinCode());
        hotel.setHotelImages(hotelDto.getHotelImages());

        Hotel updatedHotel = this.hotelRepository.save(hotel);
        return this.modelMapper.map(updatedHotel, HotelDto.class);
    }

    @Override
    public void deleteHotel(String hotelId) {
        Hotel hotel = this.hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "hotelId", hotelId));
        this.hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelDto> getHotelByLocation(String locationId) {
        List<Hotel> hotelList = this.hotelRepository.findByLocationId(locationId);
        List<HotelDto> hotelDtos = hotelList.stream().map(hotel -> this.modelMapper.map(hotel, HotelDto.class)).collect(Collectors.toList());
        return hotelDtos;
    }
}
