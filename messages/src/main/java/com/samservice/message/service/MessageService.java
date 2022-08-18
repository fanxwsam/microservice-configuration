package com.samservice.message.service;

import com.samservice.message.dto.Message;
import com.samservice.message.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;

    public void saveMessage(Message msg){
        messageRepository.save(msg);
    }


}
