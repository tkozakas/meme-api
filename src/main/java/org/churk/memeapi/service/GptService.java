package org.churk.memeapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.client.GroqClient;
import org.churk.memeapi.configuration.GroqProperties;
import org.churk.memeapi.dto.GptRequest;
import org.churk.memeapi.model.GroqRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;

@Slf4j
@Service
@AllArgsConstructor
public class GptService {

    private static final HashMap<Long, LinkedList<String>> memory = new HashMap<>();
    private static final int MEMORY_SIZE_LIMIT = 1000;

    private final GroqProperties groqProperties;
    private final GroqClient groqClient;

    public String clearMemory(Long chatId) {
        memory.remove(chatId);
        return "Memory cleared.";
    }

    public String getMemory(Long chatId) {
        LinkedList<String> chatMemory = memory.getOrDefault(chatId, new LinkedList<>());
        return String.join("\n", chatMemory);
    }

    public String getGpt(GptRequest gptRequest) {
        Long chatId = gptRequest.getChatId();
        String userMessage = gptRequest.getPrompt();
        addToMemory(chatId, "User: " + userMessage);
        String fullPrompt = buildFullPrompt(chatId, userMessage);
        GroqRequest request = new GroqRequest(fullPrompt, groqProperties);
        String chatbotResponse = groqClient.getChatCompletion("Bearer " + groqProperties.getApiKey(), request)
                .getChoices().getFirst().getMessage().getContent();
        addToMemory(chatId, "Bot: " + chatbotResponse);
        return chatbotResponse;
    }

    private String buildFullPrompt(Long chatId, String userMessage) {
        String conversationMemory = getMemory(chatId);
        return groqProperties.getInitialPrompt().formatted(conversationMemory, userMessage);
    }

    private void addToMemory(Long chatId, String message) {
        memory.computeIfAbsent(chatId, k -> new LinkedList<>()).add(message);
        LinkedList<String> chatMemory = memory.get(chatId);
        if (chatMemory.size() > MEMORY_SIZE_LIMIT) {
            chatMemory.removeFirst();
        }
    }
}
