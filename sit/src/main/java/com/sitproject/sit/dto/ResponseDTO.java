package com.sitproject.sit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ResponseDTO {
    private HttpStatus status;
    private String code;
    private String message;
    private  Object content;
    private String token;
    private String role;
    private String username;
}
