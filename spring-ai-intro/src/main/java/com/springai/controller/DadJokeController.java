package com.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 01 Jul, 2024
 */

@RestController
public class DadJokeController {
    private final ChatClient chatClient;

    public DadJokeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "dad-jokes")
    public String getDadJokes() {
        SystemMessage systemMessage = new SystemMessage("Your primary function is to tell Dad Jokes, " +
                "If someone asks you any other joke please tell them you only know Dad Jokes");

        UserMessage userMessage = new UserMessage("Tell me a joke about the universe");

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping(path = "dad-jokes2")
    public String jokes(@RequestParam(value = "topic", defaultValue = "Dogs") String topic){

        PromptTemplate promptTemplate = new PromptTemplate("Tell me a dad joke about {topic}");
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getContent();
    }
}
