package com.samservice.message.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @SequenceGenerator(name ="message_id_sequence",
            sequenceName = "message_id_sequence")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_id_sequence")
    private Integer message_id;
    private Integer toCustomerId;
    private String toCustomerEmail;
    private String sender;
    private String message;
    private LocalDateTime sentAt;
}
