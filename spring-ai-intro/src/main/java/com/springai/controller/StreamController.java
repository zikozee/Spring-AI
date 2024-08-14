package com.springai.controller;

import com.springai.output.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 14 Aug, 2024
 */

@RestController
public class StreamController {

    private final ChatClient chatClient;

    public StreamController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }


    @GetMapping(path = "stream")
    public Flux<String> stream() {
        return chatClient.prompt()
                .user("I am travelling to the Kansas City next week what are 10 of the best BBQ joints in the city")
                .stream().content();
    }

}
