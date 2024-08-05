package com.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 08 Jul, 2024
 */

@RestController
@RequestMapping(path = "olympics")
public class StuffThePromptController {

    private final ChatClient chatClient;

    @Value("classpath:prompts/olympic-sports.st")
    private Resource olympicSportsResource;
    @Value("classpath:docs/olympic-sports.txt")
    private Resource docsToStuffResource;

    public StuffThePromptController(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "2024")
    public String olympicsSports(
            @RequestParam(value = "message",
                    defaultValue = "What sports are being included in the 2024 Summer Olympics") String message,
            @RequestParam(value = "stuffIt", defaultValue = "false") boolean stuffIt) {

        PromptTemplate template = new PromptTemplate(olympicSportsResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        if(stuffIt){
            map.put("context", docsToStuffResource);
        }else {
            map.put("context", "");
        }

        Prompt prompt = template.create(map);
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return response.getResult().getOutput().getContent();
    }
}
