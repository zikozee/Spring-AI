package com.springai.controller;

import com.springai.model.*;
import com.springai.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Jun, 2024
 */

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final OpenAIService aiService;
    @PostMapping(path = "ask")
    public Answer askQuestion(@RequestBody Question question) {
        return aiService.getAnswer(question);
    }

    @PostMapping(path = "capital")
    public Answer getCapital(@RequestBody GetCapitalRequest capitalRequest) {
        return aiService.getCapital(capitalRequest);
    }

    @PostMapping(path = "capital-with-info")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest capitalRequest) {
        return aiService.getCapitalWithInfo(capitalRequest);
    }

    @PostMapping(path = "capital-2")
    public GetCapitalResponse getCapitalResponse(@RequestBody GetCapitalRequest capitalRequest) {
        return aiService.getCapitalResponse(capitalRequest);
    }

    @PostMapping(path = "capital-with-info-2")
    public GetCapitalResponseWithInfo getCapitalWithInfo2(@RequestBody GetCapitalRequest capitalRequest) {
        return aiService.getCapitalResponseWithInfo(capitalRequest);
    }





}
