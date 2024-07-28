package org.churk.memeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record TextToSpeechRequest(String text, @JsonProperty("model_id") String modelId,
                                  @JsonProperty("voice_settings") Map<String, Object> voiceSettings) {
}
