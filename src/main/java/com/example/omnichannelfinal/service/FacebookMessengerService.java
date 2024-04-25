package com.example.omnichannelfinal.service;

import com.example.omnichannelfinal.model.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FacebookMessengerService {
    @Value("${page1.access.token}")
    private String page1AccessToken;

    @Value("${page2.access.token}")
    private String page2AccessToken;

    public void sendMessage(ChatMessage chatMessage) {
        String recipientId = chatMessage.getCustomerId();
        String message = chatMessage.getMessageText();
        String accessToken;

        // Xác định access token dựa trên senderId
        if (chatMessage.getPageId().equals("316183384902816")) {
            accessToken = page1AccessToken;
        } else {
            accessToken = page2AccessToken;
        }

        String url = "https://graph.facebook.com/v19.0/me/messages?access_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"recipient\":{\"id\":\"" + recipientId + "\"}," +
                "\"message\":{\"text\":\"" + message + "\"}}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Message sent successfully.");
        } else {
            System.err.println("Failed to send message. Response: " + response.getBody());
        }
    }
}
