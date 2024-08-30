package com.adityagautam.HotelService.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {


    String uploadFile(MultipartFile file, String path, String hotelName) throws IOException;

    InputStream getResource(String path,String imageName, String hotelName) throws FileNotFoundException;

}
