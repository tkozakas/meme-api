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

    private final GroqProperties groqProperties;
    private final GroqClient groqClient;

    public String clearMemory(Long chatId) {
        memory.remove(chatId);
        return "Memory cleared.";
    }

    public String getGpt(GptRequest gptRequest) {
        String memoryPrompt = groqProperties.getInitialPrompt();
        String message = removeUnnecessaryCharacters(gptRequest.getPrompt());
        String prompt = memoryPrompt.formatted(getFormattedMemory(gptRequest.getChatId(), gptRequest.getUsername()), message);
        addToMemory(gptRequest.getChatId(), message);
        GroqRequest request = new GroqRequest(prompt, groqProperties);
        System.out.printf("Prompt: %s\n", prompt);
        return groqClient.getChatCompletion("Bearer " + groqProperties.getApiKey(), request)
                .getChoices().getFirst().getMessage().getContent();
    }

    private String getFormattedMemory(Long chatId, String username) {
        LinkedList<String> chatMemory = memory.getOrDefault(chatId, new LinkedList<>());
        StringBuilder formattedMemory = new StringBuilder();
        for (String msg : chatMemory) {
            formattedMemory.append("you: ").append(msg).append("\n");
            formattedMemory.append(username).append(": ").append(msg).append("\n");
        }
        return formattedMemory.toString();
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
