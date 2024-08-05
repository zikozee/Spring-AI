package com.zee.aidocs.command;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.Resource;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 22 Jul, 2024
 */

//@Command
@ShellComponent
public class SpringAssistantCommand {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final Resource sbPromptTemplate;

    public SpringAssistantCommand(ChatClient.Builder chatClientBuilder, VectorStore vectorStore,
                                  @Value("classpath:prompts/spring-boot-reference.st") Resource sbPromptTemplate) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
        this.sbPromptTemplate = sbPromptTemplate;
    }


    @ShellMethod(key = "q")
//    @Command(command = "q")
    public String question(@ShellOption(defaultValue = "What is Spring Boot") String message) {
        PromptTemplate promptTemplate = new PromptTemplate(sbPromptTemplate);
        Map<String, Object> promptParams = Map.of("input", message, "documents", String.join("\n", findSimilarDocuments(message)));

        return chatClient.prompt(promptTemplate.create(promptParams))
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    private List<String> findSimilarDocuments(String message) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(3));
        return documents.stream()
                .map(Document::getContent)
                .toList();
    }


}
