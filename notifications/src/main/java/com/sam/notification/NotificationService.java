package com.sam.notification;

import com.sam.clients.notifications.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void sendMessage(NotificationRequest notificationRequest){
        Notification notification = Notification.builder()
                .message(notificationRequest.message())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .toCustomerId(notificationRequest.toCustomerId())
                .sentAt(LocalDateTime.now())
                .sender("Sam's Service : Customer")
                .build();

        notificationRepository.save(notification);
    }


}
