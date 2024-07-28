package org.churk.memeapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramService {

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot%s/%s";
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.chat.id}")
    private String chatId;

    public void sendHtmlMessage(String message) {
        String url = String.format(TELEGRAM_API_URL, botToken, "sendMessage");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = String.format("chat_id=%s&text=%s&parse_mode=HTML", chatId, message);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, entity, String.class);
    }
}
