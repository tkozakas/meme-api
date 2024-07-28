package org.churk.memeapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.client.GroqClient;
import org.churk.memeapi.configuration.GroqProperties;
import org.churk.memeapi.model.GroqRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GptService {
    private final GroqProperties groqProperties;
    private final GroqClient groqClient;

    public String getGpt(String text) {
        String prompt = removeUnnecessaryCharacters(text);
        GroqRequest request = new GroqRequest(prompt, groqProperties);
        return groqClient.getChatCompletion("Bearer " + groqProperties.getApiKey(), request)
                .getChoices().getFirst().getMessage().getContent();
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
