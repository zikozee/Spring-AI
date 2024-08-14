package com.springai.controller;

import com.springai.output.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 14 Aug, 2024
 */

@RequestMapping(path = "sample")
@RestController
public class SampleController {

    private final ChatClient chatClient;

    public SampleController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a loud assistant that responds with all capital letters.")
                .build();
    }

    @GetMapping
    public String getDadJokes() {
        return chatClient
                .prompt()
                .user("Tell me a joke about the universe")
                .call()
                .content();
    }

    @GetMapping(path = "jokes")
    public String jokes(@RequestParam(value = "topic") String topic) {
        return chatClient
                .prompt()
                .user(userSpec -> userSpec.text("Tell me a dad joke about {topic}").param("topic", topic))
//                .user(userSpec -> userSpec.media(MimeTypeUtils.APPLICATION_JSON, new ClassPathResource("docs/")))
                .call()
                .content();
    }


}
