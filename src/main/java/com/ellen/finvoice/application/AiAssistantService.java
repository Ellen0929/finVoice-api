package com.ellen.finvoice.application;

import com.ellen.finvoice.infrastructure.ai.TransactionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class AiAssistantService {

    private final ChatClient chatClient;

    public AiAssistantService(
            ChatClient.Builder chatClientBuilder,
            TransactionTools transactionTools,
            @Value("classpath:prompts/system-message.st") Resource systemMessageResource
    ) throws IOException {

        String systemMessage = systemMessageResource
                .getContentAsString(StandardCharsets.UTF_8);

        this.chatClient = chatClientBuilder
                .defaultSystem(systemMessage)
                .defaultTools(transactionTools)
                .build();
    }

    public String ask(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
