package org.churk.memeapi.client;

import org.churk.memeapi.model.GroqRequest;
import org.churk.memeapi.model.GroqResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "groqClient", url = "https://api.groq.com/openai/v1")
public interface GroqClient {

    @PostMapping(value = "/chat/completions", consumes = "application/json", produces = "application/json")
    GroqResponse getChatCompletion(
            @RequestHeader("Authorization") String authorization,
            @RequestBody GroqRequest request
    );

}
