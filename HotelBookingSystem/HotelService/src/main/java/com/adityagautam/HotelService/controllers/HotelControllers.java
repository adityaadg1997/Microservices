package com.adityagautam.HotelService.controllers;

import com.adityagautam.HotelService.exception.HotelBaseException;
import com.adityagautam.HotelService.payloads.ApiResponse;
import com.adityagautam.HotelService.payloads.HotelDto;
import com.adityagautam.HotelService.payloads.ImageResponse;
import com.adityagautam.HotelService.services.FileService;
import com.adityagautam.HotelService.services.HotelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    //Download Hotel image
    @GetMapping("/{hotelId}/media/download")
    public void downloadHotelImages(@PathVariable String hotelId, HttpServletResponse response) throws IOException {
        List<String> hotelImages = Optional.ofNullable(hotelService.getHotel(hotelId).getHotelImages()).orElseThrow(() -> new HotelBaseException("Hotel with hotelId : "+hotelId+" not found."));  // Get file names from the database

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-Disposition", "attachment; filename=hotel-images.zip");
        response.setContentType("application/zip");

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (String imageName : hotelImages) {
                try (InputStream fis = fileService.getResource(imagePath, imageName, hotelId)) {
                    ZipEntry zipEntry = new ZipEntry(imageName);
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
            zipOut.finish();
        }catch (IOException ioException){
            throw new IOException("Unable to download bulk hotel images");
        }
    }
}
