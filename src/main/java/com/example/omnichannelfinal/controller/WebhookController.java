package com.example.omnichannelfinal.controller;

import com.example.omnichannelfinal.model.ChatMessage;
import com.example.omnichannelfinal.model.WebhookPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    private static final String VERIFY_TOKEN = "12345";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public String handleGetRequest(@RequestParam("hub.mode") String mode,
                                   @RequestParam("hub.verify_token") String token,
                                   @RequestParam("hub.challenge") String challenge) {
        System.out.println("-------------- New Request GEThub.verify_token --------------");

        System.out.println("Body: mode=" + mode + ", token=" + token + ", challenge=" + challenge);

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            System.out.println("WEBHOOK_VERIFIED");
            return challenge;
        } else {
            System.out.println("Responding with 403 Forbidden");
            return "403 Forbidden";
        }
    }
    @PostMapping
    @MessageMapping("/messages")
    public void handlePostRequest(@RequestBody WebhookPayload payload) {
        System.out.println("-------------- New Request POST --------------");

        System.out.println("Body:"+ payload);

        for (WebhookPayload.Entry entry : payload.getEntry()) {

            for (WebhookPayload.Messaging messaging : entry.getMessaging()) {

                System.out.println("Message ID: " + messaging.getMessage().getMid());
                System.out.println("Message Text: " + messaging.getMessage().getText());

                System.out.println("Sender ID: " + messaging.getSender().getId());

                System.out.println("Recipient ID: " + messaging.getRecipient().getId());

                long timestamp = messaging.getTimestamp();
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
                System.out.println("Time: " + dateTime );

                handleMessage(messaging);
            }
        }
    }

    private void handleMessage(WebhookPayload.Messaging messaging) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageID(messaging.getMessage().getMid());
        chatMessage.setMessageText(messaging.getMessage().getText());
        chatMessage.setCustomerId(messaging.getSender().getId());
        chatMessage.setPageId(messaging.getRecipient().getId());

        long timestamp = messaging.getTimestamp();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        chatMessage.setDateTime(dateTime.toString());

        messagingTemplate.convertAndSend("/topic/messages", chatMessage);

    }
}
