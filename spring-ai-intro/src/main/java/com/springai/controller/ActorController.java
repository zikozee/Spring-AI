package com.springai.controller;

import com.springai.output.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 14 Aug, 2024
 */

@RestController
@RequestMapping(path = "actor")
public class ActorController {

    private final ChatClient chatClient;

    public ActorController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }

    @GetMapping(path = "films-string")
    public String getActorFilmsString(){
        return chatClient.prompt()
                .user("Generate a filmography for a Anthony Hopkins for the year 2010.")
                .call()
                .content();
    }

    @GetMapping(path = "films")
    public ActorFilms getActorFilms(){
        return chatClient.prompt()
                .user("Generate a filmography for a Anthony Hopkins.")
                .call()
                .entity(ActorFilms.class);
    }

    @GetMapping(path = "films-list")
    public List<ActorFilms> listActorFilms(){
        return chatClient.prompt()
                .user("Generate a filmography for the actors Denzel Washington, Leonardo Dicaprio and Tom Hanks")
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }

}
