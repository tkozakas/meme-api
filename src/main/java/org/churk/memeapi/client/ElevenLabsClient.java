package org.churk.memeapi.client;

import org.churk.memeapi.model.TextToSpeechRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "elevenLabsClient", url = "https://api.elevenlabs.io/v1")
public interface ElevenLabsClient {

    @PostMapping(value = "/text-to-speech/{voiceId}/stream", consumes = "application/json", produces = "application/octet-stream")
    byte[] convertTextToSpeech(
            @RequestHeader("xi-api-key") String apiKey,
            @PathVariable("voiceId") String voiceId,
            @RequestBody TextToSpeechRequest request
    );
}
