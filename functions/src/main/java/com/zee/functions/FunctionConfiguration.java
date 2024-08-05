package com.zee.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 05 Aug, 2024
 */

@Configuration
public class FunctionConfiguration {

    @Bean
    @Description("Get the current weather conditions for the given city")
    public Function<WeatherService.Request, WeatherService.Response> currentWeatherFunction(WeatherConfigProperties props) {

        return new WeatherService(props);
    }
}
