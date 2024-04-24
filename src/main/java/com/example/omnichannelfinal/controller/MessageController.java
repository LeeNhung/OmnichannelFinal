package com.example.omnichannelfinal.controller;

import com.example.omnichannelfinal.model.ChatMessage;
import com.example.omnichannelfinal.service.FacebookMessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private FacebookMessengerService facebookMessengerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sendMessage")
    @MessageMapping("/messages")
    public void sendMessage(@RequestBody ChatMessage chatMessage){

        facebookMessengerService.sendMessage(chatMessage);

        messagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }
}
