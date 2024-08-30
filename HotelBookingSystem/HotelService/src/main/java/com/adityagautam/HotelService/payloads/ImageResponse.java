package com.adityagautam.HotelService.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    private List<String> imageNameList;
    private String message;
    private boolean success;
    private HttpStatus status;
}
