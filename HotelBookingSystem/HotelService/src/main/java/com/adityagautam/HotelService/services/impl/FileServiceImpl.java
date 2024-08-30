package com.adityagautam.HotelService.services.impl;


import com.adityagautam.HotelService.exception.BadApiRequestException;
import com.adityagautam.HotelService.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    public String uploadFile(MultipartFile file, String basePath, String hotelName) throws IOException {

        try {
            // Construct the path with the hotelName
            String path = basePath + File.separator + hotelName + File.separator;

            // abc.png
            String originalFilename = file.getOriginalFilename();

            String filename = UUID.randomUUID().toString();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNameWithExtension = filename + extension;
            String fullPathWithFileName = path + fileNameWithExtension;

            if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

                // Create a new File object for the folder
                File folder = new File(path);
                if (!folder.exists()) {
                    log.info("Folder does not exist. Creating new folder.");
                    folder.mkdirs();  // Create the directory if it does not exist
                }

                // Upload the file
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
                return fileNameWithExtension;

            } else {
                throw new BadApiRequestException("File with this " + extension + " not allowed!");
            }
        } catch (IOException e) {
            throw new IOException("Unable to upload bulk hotel images :: [FileServiceImpl]");
        }
    }


    @Override
    public InputStream getResource(String path, String imageName, String hotelName) throws FileNotFoundException {
        try {
            String fullPath = path + File.separator + hotelName + File.separator + imageName;
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File" + "fileName" + imageName);
        }
    }

}
