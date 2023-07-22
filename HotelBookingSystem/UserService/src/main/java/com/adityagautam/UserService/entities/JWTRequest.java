package com.adityagautam.UserService.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTRequest {

    private String email;
    private String password;
}
