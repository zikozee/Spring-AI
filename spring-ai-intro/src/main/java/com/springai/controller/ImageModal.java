package com.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 11 Aug, 2024
 */

@RestController
public class ImageModal {

    private final ChatClient chatClient;
    private final Resource imageResource;
    private final Resource codeImageResource;

    public ImageModal(ChatClient.Builder chatClientBuilder, @Value("classpath:images/sincerely-media-2UlZpdNzn2w-unsplash.jpg") Resource imageResource,
                      @Value("classpath:images/java-open-ai.png") Resource codeImageResource) {
        this.chatClient = chatClientBuilder.build();
        this.imageResource = imageResource;
        this.codeImageResource = codeImageResource;
    }

    @GetMapping(path = "image-describe")
    public String imageDescribe() throws IOException {

        UserMessage userMessage = new UserMessage(
                "Can you please explain what you see in the following image",
//                new Media(MimeTypeUtils.IMAGE_JPEG, imageResource)
                new Media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("images/sincerely-media-2UlZpdNzn2w-unsplash.jpg"))
        );

        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping(path = "code-describe")
    public String codeDescribe() throws IOException {

        UserMessage userMessage = new UserMessage(
                "The following is a screenshot of some code. Can you do your best to provide a description of what the code is doing",
                new Media(MimeTypeUtils.IMAGE_PNG, codeImageResource)
//                new Media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("images/java-open-ai.png"))
        );

        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping(path = "image-to-code")
    public String imageToCode() throws IOException {

        UserMessage userMessage = new UserMessage(
                "The following is a screenshot of some code. Can you translate this from image into actual code",
                new Media(MimeTypeUtils.IMAGE_PNG, codeImageResource)
//                new Media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("images/java-open-ai.png"))
        );

        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }
}
