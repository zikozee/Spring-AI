package com.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Jun, 2024
 */


public record GetCapitalResponseWithInfo(
        @JsonPropertyDescription("This is the city name") String answer,
        @JsonPropertyDescription("This is the location") String location,
        @JsonPropertyDescription("This is the current population of the city") long population,
        @JsonPropertyDescription("This is the language spoken") String language,
        @JsonPropertyDescription("This is the currency used") String currency,
        @JsonPropertyDescription("This is the name of the founder") String founder

) {
}
