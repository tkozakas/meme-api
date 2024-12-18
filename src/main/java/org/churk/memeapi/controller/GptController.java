package org.churk.memeapi.controller;

import lombok.AllArgsConstructor;
import org.churk.memeapi.dto.GptRequest;
import org.churk.memeapi.service.GptService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/prompt")
    public ResponseEntity<String> getGpt(@RequestBody GptRequest gptRequest) {
        String gpt = gptService.getGpt(gptRequest);
        return gpt == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(gpt);
    }

    /**
     * Clear the memory of the chat.
     *
     * @param chatId The chat to clear the memory from.
     * @return The memory cleared message.
     */
    @PostMapping(value = "/clear/{chatId}")
    public ResponseEntity<String> clearMemory(@PathVariable("chatId") Long chatId) {
        String message = gptService.clearMemory(chatId);
        return message == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(message);
    }

    @PostMapping(value = "/memory/{chatId}")
    public ResponseEntity<String> getMemory(@PathVariable("chatId") Long chatId) {
        String memory = gptService.getMemory(chatId);
        return memory == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(memory);
    }
}
