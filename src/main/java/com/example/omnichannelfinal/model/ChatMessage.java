package com.example.omnichannelfinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;
    private String messageID;
    private String messageText;
    private String customerId;
    private String pageId;
    private String dateTime;


    public ChatMessage( String messageID, String messageText, String customerId, String pageId, String dateTime) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.customerId = customerId;
        this.pageId = pageId;
        this.dateTime = dateTime;
    }
}
