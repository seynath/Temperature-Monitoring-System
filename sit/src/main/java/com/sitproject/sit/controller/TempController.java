package com.sitproject.sit.controller;

import com.sitproject.sit.dto.TempDTO;
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


@PostMapping("/save-temp")
public ResponseEntity<TempDTO> saveTemp(@RequestBody TempDTO tempDTO) {
    tempService.saveTemp( tempDTO);
//    messagingTemplate.convertAndSend("/topic/temperature", tempDTO);
    return new ResponseEntity<>( tempDTO, HttpStatus.CREATED);

}

@GetMapping("/hi")
public String sendHello(){
    return ("Hello Dala");
}



}
