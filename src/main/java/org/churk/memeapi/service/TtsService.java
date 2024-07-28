package org.churk.memeapi.service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.client.ElevenLabsClient;
import org.churk.memeapi.configuration.ElevenLabsProperties;
import org.churk.memeapi.model.TextToSpeechRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class TtsService {
    private final ElevenLabsProperties elevenLabsProperties;
    private final ElevenLabsClient elevenLabsClient;

    public byte[] getTts(String text) {
        List<String> apiKey = elevenLabsProperties.getApiKey();
        String voiceId = elevenLabsProperties.getVoiceId();

        String prompt = removeUnnecessaryCharacters(text);
        TextToSpeechRequest request = new TextToSpeechRequest(
                prompt,
                "eleven_multilingual_v2",
                Map.of(
                        "stability", 0.5,
                        "similarity_boost", 0.8,
                        "style", 0.0,
                        "use_speaker_boost", true
                )
        );
        for (String key : apiKey) {
            try {
                return elevenLabsClient.convertTextToSpeech(key, voiceId, request);
            } catch (FeignException e) {
                log.error("Error while converting text to speech for api-key: {}", key, e);
            }
        }
        return null;
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
