package com.sitproject.sit.controller;

import com.sitproject.sit.dto.ResponseDTO;
import com.sitproject.sit.dto.UserDTO;
import com.sitproject.sit.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/api/v/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/save-user")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {
        System.out.println("Received User");
        ResponseDTO responseDTO = authenticationService.saveUser( userDTO);
        return new ResponseEntity<>( responseDTO, responseDTO.getStatus());
    }

    @PostMapping("/validate-user")
    public ResponseEntity validateUser(@RequestBody UserDTO userDTO) {
        System.out.println("Validating User");
        ResponseDTO responseDTO = authenticationService.validUser( userDTO);
        return new ResponseEntity<>( responseDTO, responseDTO.getStatus());
    }
}
