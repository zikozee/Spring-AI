package com.springai.controller;

import com.springai.model.Author;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 07 Jul, 2024
 */

@RestController
@RequestMapping(path = "converter")
public class ConverterController {


    private final ChatClient chatClient;

    public ConverterController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping(path = "songs")
    public String getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Taylor Swift") String artist) {
        String message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know, just say "I don't know"
                """;

        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("artist", artist));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

    @GetMapping(path = "songs-list")
    public List<String> getSongsListByArtist(@RequestParam(value = "artist", defaultValue = "Taylor Swift") String artist) {
        String message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know, just say "I don't know".
                {format}
                """;

        ListOutputConverter converter = new ListOutputConverter(new DefaultConversionService());

        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("artist", artist, "format", converter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return converter.convert(response.getResult().getOutput().getContent());
    }

    @GetMapping(path = "author/{author}")
    public Map<String, Object> authorsSocialLinks(@PathVariable(value = "author") String author) {
        String message = """
                Generate a list of links for the author {author}. Include the authors name as the key and any social network links as the object.
                {format}
                """;

        MapOutputConverter converter = new MapOutputConverter();

        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("author", author, "format", converter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return converter.convert(response.getResult().getOutput().getContent());
    }

    @GetMapping(path = "by-author")
    public Author getBooksByAuthor(@RequestParam(value = "author", defaultValue = "Myles Munroe") String author){
        String message = """
                Generate a list of books written by the author {author}. If you aren't positive that a book
                belongs to this author please don't include it.
                {format}
                """;

        BeanOutputConverter<Author> beanConverter  = new BeanOutputConverter<>(Author.class);
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("author", author, "format", beanConverter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return beanConverter.convert(response.getResult().getOutput().getContent());
    }
}
