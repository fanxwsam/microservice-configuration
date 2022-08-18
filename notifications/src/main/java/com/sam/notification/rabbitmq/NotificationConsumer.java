package com.sam.notification.rabbitmq;

import com.sam.clients.notifications.NotificationRequest;
import com.sam.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest){
        log.info("Consumed {} from queue", notificationRequest);
//        try {
//            Thread.sleep(20 * 1000);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
        notificationService.sendMessage(notificationRequest);
    }
}
