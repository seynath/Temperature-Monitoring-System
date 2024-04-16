package com.sitproject.sit.service;
import com.sitproject.sit.dto.TempDTO;
import com.sitproject.sit.entity.Temp;
import com.sitproject.sit.repository.TempRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TempService {
    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

        public void saveTemp( TempDTO tempDTO) {
            Temp temp = new Temp(tempDTO.getDevice_id(), tempDTO.getTemperature(), tempDTO.getTimestamp());
            tempRepository.save(temp);
            System.out.println("Tracked Temperature to DB");
            messagingTemplate.convertAndSend("/topic/temperature", tempDTO);
            System.out.println("Sent Temperature to WebSocket");
        }
}
