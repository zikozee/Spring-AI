package com.zee.functions;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 05 Aug, 2024
 */

@ConfigurationProperties(prefix = "weather")
public record WeatherConfigProperties(String apiKey, String apiUrl) {
}
