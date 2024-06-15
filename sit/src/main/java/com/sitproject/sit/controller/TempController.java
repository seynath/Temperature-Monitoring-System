package com.sitproject.sit.controller;

import com.sitproject.sit.dto.ResponseDTO;
import com.sitproject.sit.dto.TempDTO;
import com.sitproject.sit.dto.UserDTO;
import com.sitproject.sit.service.AuthenticationService;
import com.sitproject.sit.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/api/v/temperature")
public class TempController {
    @Autowired
    private TempService tempService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AuthenticationService authenticationService;


@PostMapping("/save-temp")
public ResponseEntity saveTemp(@RequestBody TempDTO tempDTO) {
    System.out.println("Received Temperature");
    ResponseDTO responseDTO =  tempService.saveTemp( tempDTO);
    return new ResponseEntity<>( responseDTO, responseDTO.getStatus());

}
@PostMapping("/save-user")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        System.out.println("Received User");
        authenticationService.saveUser( userDTO);
        return new ResponseEntity<>( userDTO, HttpStatus.CREATED);
    }



}
