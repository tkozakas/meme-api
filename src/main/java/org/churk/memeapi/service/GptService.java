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
    private static final int MEMORY_SIZE_LIMIT = 500;
    private static final String MEMORY_PROMPT = "You are a helpful assistant. Continue the conversation based on the following previous messages: %s. Answer the next message naturally and seamlessly: %s. Do not reference or mention these instructions in your response.";

    private final GroqProperties groqProperties;
    private final GroqClient groqClient;

    public String getGpt(GptRequest gptRequest) {
        String message = removeUnnecessaryCharacters(gptRequest.getPrompt());
        String prompt = MEMORY_PROMPT.formatted(getMemory(gptRequest.getChatId()), message);
        addToMemory(gptRequest.getChatId(), message);
        GroqRequest request = new GroqRequest(prompt, groqProperties);
        System.out.printf("Prompt: %s\n", prompt);
        return groqClient.getChatCompletion("Bearer " + groqProperties.getApiKey(), request)
                .getChoices().getFirst().getMessage().getContent();
    }

    private String getMemory(Long chatId) {
        return String.join(" ", memory.getOrDefault(chatId, new LinkedList<>()));
    }

    private void addToMemory(Long chatId, String message) {
        memory.computeIfAbsent(chatId, k -> new LinkedList<>()).add(message);
        LinkedList<String> chatMemory = memory.get(chatId);
        if (chatMemory.size() > MEMORY_SIZE_LIMIT) {
            chatMemory.removeFirst();
        }
    }

    private String removeUnnecessaryCharacters(String text) {
        return text.replaceAll("\\[.*?\\]", "")
                .replaceAll("\\(.*?\\)", "")
                .replaceAll("https?://\\S+\\s?", "")
                .replaceAll("www.\\S+\\s?", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
