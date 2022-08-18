package com.sam.customer;


import com.sam.amqp.RabbitMQMessageProducer;
import com.sam.clients.fraud.FraudCheckResponse;
import com.sam.clients.fraud.FraudClient;
import com.sam.clients.notifications.NotificationClient;
import com.sam.clients.notifications.NotificationRequest;
import com.samservice.kafka.MessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    private final RabbitMQMessageProducer producer;
    private KafkaTemplate<String, MessageRequest> kafkaTemplate;


    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();



        // Todo: check email valid
        // Todo: check email not taken
        // Todo: check if fraudster
        // store Customer
        customerRepository.saveAndFlush(customer);

        /*  FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );*/

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }



        // Todo: send notification - make it async, add it to queue

//        notificationClient.invokeNotification(new NotificationRequest(customer.getId(),
//                customer.getEmail(),
//                String.format("Hi %s, welcome to Sam's service", customer.getFirstName())
//                ));



//        producer.publish(
//                new NotificationRequest(customer.getId(),
//                                        customer.getEmail(),
//                                         String.format("Hi %s, welcome to Sam's service", customer.getFirstName())),
//                                notificationConfig.getInternalExchange(),
//                                notificationConfig.getNotificationQueue()
//                        );

        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Sam's service", customer.getFirstName()));

        // publish message to RabbitMQ
        producer.publish(
                                notificationRequest,
                                "internal.exchange",
                                "internal.notification.routing-key"
                        );

        //publish message to Kafka
        MessageRequest messageRequest = new MessageRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Sam's service", customer.getFirstName()),
                LocalDateTime.now());
        kafkaTemplate.send("samservice", messageRequest);

    }

}
