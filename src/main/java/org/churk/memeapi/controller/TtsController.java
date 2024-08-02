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
        byte[] tts = ttsService.getTts(text);
        return tts == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(tts);
    }
}
