package com.ssafy.fitpass.ai.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final OpenAiChatModel openAiChatModel;

    AiService(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

//    public
}
