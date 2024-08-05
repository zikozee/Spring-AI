package com.zee.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 05 Aug, 2024
 */

@RestController
public class CityController {

    private final ChatClient chatClient;

    public CityController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "cities")
    public String cities(@RequestParam(value = "message") String message) {
        SystemMessage systemMessage = new SystemMessage("You are a helpful AI assistant answering questions about cities");
        UserMessage userMessage = new UserMessage(message);

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("currentWeatherFunction")
                .build();

        ChatResponse chatResponse = chatClient.prompt(
                new Prompt(
                        List.of(systemMessage, userMessage),
                        chatOptions
                )).call().chatResponse();

        return chatResponse.getResult().getOutput().getContent();
    }
}
