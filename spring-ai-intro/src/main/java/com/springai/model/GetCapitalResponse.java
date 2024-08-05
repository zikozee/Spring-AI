package com.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Jun, 2024
 */


public record GetCapitalResponse(@JsonPropertyDescription("This is the city name") String answer) {
}
