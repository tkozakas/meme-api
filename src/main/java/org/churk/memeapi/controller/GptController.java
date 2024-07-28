package org.churk.memeapi.controller;

import lombok.AllArgsConstructor;
import org.churk.memeapi.service.GptService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
@AllArgsConstructor
public class GptController {
    private final GptService gptService;

    /**
     * Get a GPT response from the text.
     *
     * @param prompt The text to get the GPT response from.
     * @return The GPT response.
     */
    @GetMapping(value = "/prompt", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getGpt(@RequestBody String prompt) {
        String gpt = gptService.getGpt(prompt);
        return gpt == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(gpt);
    }
}
