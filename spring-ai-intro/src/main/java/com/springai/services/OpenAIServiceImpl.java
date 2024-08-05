package com.springai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springai.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 04 May, 2024
 */

@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient; //todo:: use this or
    private final OpenAiChatModel openAiChatModel; //todo::  this
    private final ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder, OpenAiChatModel openAiChatModel, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.openAiChatModel = openAiChatModel;
        this.objectMapper = objectMapper;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-prompt-2.st")
    private Resource getCapitalPrompt2;

    @Value("classpath:templates/get-capital-prompt-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        log.info("Question asked: " + question);

        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(GetCapitalRequest capitalRequest) {

//        PromptTemplate promptTemplate = new PromptTemplate("what is the capital of " + capitalRequest.stateOrCountry() + "?");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt/*, Map.of("stateOrCountry", capitalRequest)*/);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest));
        ChatResponse response = openAiChatModel.call(prompt);

        log.info("\n{}", response.getResult().getOutput().getContent());
        String responString;
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
            responString = jsonNode.get("answer").asText();
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Answer(responString);
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest capitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest));
        ChatResponse response = openAiChatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponse getCapitalResponse(GetCapitalRequest capitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt2);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest, "format", format));
        ChatResponse response = openAiChatModel.call(prompt);

        return converter.convert(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalResponseWithInfo getCapitalResponseWithInfo(GetCapitalRequest capitalRequest) {
        BeanOutputConverter<GetCapitalResponseWithInfo> converter = new BeanOutputConverter<>(GetCapitalResponseWithInfo.class);
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt2);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest, "format", format));
        ChatResponse response = openAiChatModel.call(prompt);

        return converter.convert(response.getResult().getOutput().getContent());
    }
}
