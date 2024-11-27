package org.churk.memeapi.dto;

import lombok.Data;

@Data
public class GptRequest {
    private String prompt;
    private Long chatId;
}
