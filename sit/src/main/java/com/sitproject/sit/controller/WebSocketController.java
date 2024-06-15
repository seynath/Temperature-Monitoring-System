package com.sitproject.sit.controller;

import com.sitproject.sit.dto.TempDTO;
import com.sitproject.sit.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class WebSocketController {
    @Autowired
    private TempService tempService;

    @MessageMapping("/client-temperature")
    @SendTo("/topic/temperature")
    public TempDTO handleTemperatureUpdate(TempDTO tempDTO, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Received Temperature from WebSocket");
        tempService.saveTemp(tempDTO);
        return tempDTO;
    }
}
