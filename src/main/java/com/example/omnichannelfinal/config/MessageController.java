package com.example.omnichannelfinal.config;

import com.example.omnichannelfinal.model.ChatMessage;
import com.example.omnichannelfinal.service.FacebookMessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private FacebookMessengerService facebookMessengerService;
    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessage chatMessage){
        facebookMessengerService.sendMessage(chatMessage);
    }
}