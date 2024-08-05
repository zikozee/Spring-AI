package com.springai.services;

import com.springai.model.*;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 04 May, 2024
 */


public interface OpenAIService {

    String getAnswer(String question);
    Answer getAnswer(Question question);
    Answer getCapital(GetCapitalRequest capitalRequest);
    Answer getCapitalWithInfo(GetCapitalRequest capitalRequest);
    GetCapitalResponse getCapitalResponse(GetCapitalRequest capitalRequest);
    GetCapitalResponseWithInfo getCapitalResponseWithInfo(GetCapitalRequest capitalRequest);
}
