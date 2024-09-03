package com.adityagautam.HotelService.controllers;

import com.adityagautam.HotelService.exception.HotelBaseException;
import com.adityagautam.HotelService.payloads.ApiResponse;
import com.adityagautam.HotelService.payloads.HotelDto;
import com.adityagautam.HotelService.payloads.ImageResponse;
import com.adityagautam.HotelService.services.FileService;
import com.adityagautam.HotelService.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/hotel")
public class HotelControllers {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private FileService fileService;

    @Value("${hotel.image.path}")
    private String imagePath;

    @PostMapping("/")
    public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotelDto){
        HotelDto addedHotel = this.hotelService.addHotel(hotelDto);
        return new ResponseEntity<>(addedHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable String hotelId){
        HotelDto hotel = this.hotelService.getHotel(hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        List<HotelDto> hotelDtos = this.hotelService.getAllHotel();
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable String hotelId, @RequestBody HotelDto hotelDto){
        HotelDto updatedHotel = this.hotelService.updateHotel(hotelId, hotelDto);
        return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotel(@PathVariable String hotelId){
        this.hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>(new ApiResponse("Hotel with hotelId "+ hotelId+ " Successfully deleted!!", true), HttpStatus.OK);
    }

    //get getHotelByLocation
    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<HotelDto>> getHotelByLocation(@PathVariable String locationId){
        List<HotelDto> hotelByLocation = this.hotelService.getHotelByLocation(locationId);
        return new ResponseEntity<>(hotelByLocation, HttpStatus.OK);
    }

    //Upload Hotel Image
    @PostMapping("/{hotelId}/media/upload")
    public ResponseEntity<ImageResponse> uploadHotelImages(@PathVariable String hotelId, @RequestParam("hotelImages") List<MultipartFile> images) throws IOException {
        try {
            if(images.size() > 5){
                 throw new DataIntegrityViolationException("Size : "+images.size());
            }
            // Retrieve the existing hotel and its images, initializing the list if necessary
            HotelDto hotelDto = hotelService.getHotel(hotelId);
            List<String> existingImages = Optional.ofNullable(hotelDto.getHotelImages()).orElseGet(ArrayList::new);

            // Upload new images and add their filenames to the list
            for (MultipartFile image : images) {
                String fileName = fileService.uploadFile(image, imagePath, hotelId);
                existingImages.add(fileName);
            }

            // Update the hotel with the new list of images
            hotelDto.setHotelImages(existingImages);
            HotelDto updatedHotel = hotelService.updateHotel(hotelId, hotelDto);

            // Build and return the response
            ImageResponse response = ImageResponse.builder()
                    .imageNameList(updatedHotel.getHotelImages())
                    .message("Hotel images are successfully uploaded!")
                    .status(HttpStatus.CREATED)
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException ioException) {
            throw new IOException("Unable to upload bulk hotel images");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new DataIntegrityViolationException("Image upload limit exceeds. A maximum of 5 images can be uploaded at once.");
        }
    }

    /**
     * Download Hotel image
     * client will call getHotelImageUrls
     * in getHotelImageUrls, we are imageUrls list and return it,
     * so that, client can call each url and will be able to download each image.
     */

    //Create an endpoint to serve images using the getResource method:
    @GetMapping("/{hotelId}/image/{imageName}")
    public ResponseEntity<InputStreamResource> serveImage(@PathVariable String hotelId, @PathVariable String imageName) {
        try {
            InputStream imageStream = fileService.getResource(imagePath, imageName, hotelId);
            InputStreamResource resource = new InputStreamResource(imageStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Generate the URLs for the images and return them in your main endpoint:
    @GetMapping("/{hotelId}/media/download")
    public ResponseEntity<List<String>> getHotelImageUrls(@PathVariable String hotelId) {
        try {
            List<String> hotelImages = Optional.ofNullable(hotelService.getHotel(hotelId).getHotelImages()).orElseThrow(() -> new HotelBaseException("Hotel with hotelId: " + hotelId + " not found."));

            String baseUrl = "/hotel/" + hotelId + "/image/";

            List<String> imageUrls = hotelImages.stream().map(imageName -> baseUrl + imageName).collect(Collectors.toList());

            return new ResponseEntity<>(imageUrls, HttpStatus.OK);
        } catch (HotelBaseException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
