package com.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 10 Jul, 2024
 */

@RestController
public class RagController {
    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;

    @Value("classpath:prompts/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    public RagController(ChatClient.Builder chatClientBuilder, SimpleVectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    @GetMapping(path = "faq")
    public String faq(@RequestParam(value = "message", defaultValue = "How to buy tickets for the Olympic Games Paris 2024") String message) {

        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(2));
        List<String> contentList = similarDocuments.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Map<String, Object> promptParams = Map.of("input", message, "documents", String.join("\n", contentList));
        Prompt prompt = promptTemplate.create(promptParams);

        return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getContent();
    }
}
