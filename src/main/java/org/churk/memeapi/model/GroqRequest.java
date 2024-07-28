package org.churk.memeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.churk.memeapi.configuration.GroqProperties;

@Data
@RequiredArgsConstructor
public class GroqRequest {
    private Message[] messages;
    private String model;
    private Integer n;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    private Double temperature;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("tool_choice")
    private Object toolChoice;

    public GroqRequest(String prompt, GroqProperties properties) {
        this.messages = new Message[]{new Message("user", prompt)};
        this.model = properties.getModel();
        this.n = properties.getN();
        this.frequencyPenalty = properties.getFrequencyPenalty();
        this.maxTokens = properties.getMaxTokens();
        this.presencePenalty = properties.getPresencePenalty();
        this.temperature = properties.getTemperature();
        this.topP = properties.getTopP();
        this.toolChoice = properties.getToolChoice();
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
