package com.zee.functions;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 05 Aug, 2024
 */

@Slf4j
public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    private final WeatherConfigProperties weatherProps;
    private final RestClient restClient;

    public WeatherService(WeatherConfigProperties weatherProps) {
        this.weatherProps = weatherProps;
        this.restClient = RestClient.create(weatherProps.apiUrl());
    }

    @Override
    public Response apply(Request request) {
        log.info("Weather Request: {}", request);
        Response response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherProps.apiKey(), request.city())
                .retrieve()
                .body(Response.class);

        log.info("Weather API Response: {}", response);
        return response;
    }


//    @JsonClassDescription("Get the current weather conditions for the given city")  //alternative to placing @Description on Bean
    public record Request(String city) {}
    public record Response(Location location, Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon) {}
    public record Current(String temp_f, String temp_c, Condition condition, String wind_mph, String humidity) {}
    public record Condition(String text){}
}
