package com.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 07 Jul, 2024
 */


public record Author(
        @JsonPropertyDescription("This is the author name") String author,
        @JsonPropertyDescription("This is the list of books") List<String> books) {
}
