package org.churk.memeapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.service.TtsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/elevenlabs")
@AllArgsConstructor
public class TtsController {
    private final TtsService ttsService;

    /**
     * Get a TTS file from the text.
     *
     * @param text The text to convert to TTS.
     * @return The TTS file.
     */
    @PostMapping(value = "/tts", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> getTts(@RequestBody String text) {
        try {
            log.debug("Received text for TTS: {}", text);
            byte[] tts = ttsService.getTts(text);
            if (tts == null) {
                log.debug("TTS service returned null for text: {}", text);
                return ResponseEntity.notFound().build();
            }
            log.debug("TTS service returned data for text: {}", text);
            return ResponseEntity.ok(tts);
        } catch (Exception e) {
            log.error("Exception encountered while processing text: {}", text, e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
