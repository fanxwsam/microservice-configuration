package com.samservice.message.kafka;

import com.samservice.kafka.MessageRequest;
import com.samservice.message.dto.Message;
import com.samservice.message.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaListeners {
    private MessageService messageService;

    // read the message and save to database
    @KafkaListener(topics="${kafka.topics}",
    groupId = "groupId")
    void listener(MessageRequest data){
        Message msg = new Message();
        msg.setMessage(data.getMessage());
        msg.setSender("Sam Service");
        msg.setSentAt(data.getCreated());
        msg.setToCustomerEmail(data.getToCustomerEmail());
        messageService.saveMessage(msg);

        log.info("$$$$$$$$$$$$   Listener received data: " + data.toString());
    }
}
