package com.springai.output;

import org.springframework.context.annotation.Description;

import java.util.List;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 14 Aug, 2024
 */


public record ActorFilms(String actor, List<Movie> movies) {

    public record Movie(@Description("the role played")String name, @Description("The movie name")String movieName, @Description("The synopsis") String synopsis) {

    }
}



