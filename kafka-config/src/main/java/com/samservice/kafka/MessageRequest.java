package com.samservice.kafka;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageRequest {
    private int toCustomerId;
    private String toCustomerEmail;
    private String message;
    private LocalDateTime created;
}
