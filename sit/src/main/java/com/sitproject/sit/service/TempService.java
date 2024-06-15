package com.sitproject.sit.service;
import com.sitproject.sit.dto.ResponseDTO;
import com.sitproject.sit.dto.TempDTO;
import com.sitproject.sit.entity.Temp;
import com.sitproject.sit.entity.User;
import com.sitproject.sit.repository.TempRepository;
import com.sitproject.sit.repository.UserRepo;
import com.sitproject.sit.util.varList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TempService {
    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailService mailService;

    @Value("${max.allowed.temperature}")
    private double MAX_ALLOWED_TEMPERATURE;

        public ResponseDTO saveTemp(TempDTO tempDTO) {
            ResponseDTO responseDTO = new ResponseDTO();
            try {
                Temp temp = new Temp(tempDTO.getDevice_id(), tempDTO.getTemperature(), tempDTO.getTimestamp());
                tempRepository.save(temp);
                System.out.println("Tracked Temperature to DB");
                messagingTemplate.convertAndSend("/topic/temperature", tempDTO);
                System.out.println("Sent Temperature to WebSocket");

                // Check if temperature exceeds the limit
                if (temp.getTemperature() > MAX_ALLOWED_TEMPERATURE) {
                    String subject = "Temperature Alert";
                    String body = "The temperature is above the allowed limit. Device ID: " + temp.getDevice_id() + ", Temperature: " + temp.getTemperature() + "°C, Timestamp: " + temp.getTimestamp();
                    List<String> recipients = getRecipients();
                    mailService.sendEmail(recipients, subject, body);
                }

                responseDTO.setCode(varList.RSP_SUCCES);
                responseDTO.setMessage("Success");
                responseDTO.setContent(tempDTO);
                responseDTO.setStatus(HttpStatus.ACCEPTED);
            }catch (Exception e) {
                responseDTO.setCode(varList.RSP_FAIL);
                responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                responseDTO.setMessage(e.getMessage());
                return responseDTO;
            }
            return responseDTO;
        }

//    private List<String> getRecipients() {
//        // Implement logic to retrieve recipients from a predefined list or another source
//        return List.of("hashanruchira02@gmail.com", "ishadyaap@gmail.com");
//    }
    private List<String> getRecipients(){
            List<User> users = userRepo.findAll();
            List<String> emailList = new ArrayList<>();
            for(User us: users){
                emailList.add(us.getUsername());
            }
        System.out.println(emailList);
            return emailList;
    }
}

