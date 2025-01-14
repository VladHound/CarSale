package car.sale.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public PathPatternParser parser() {
        return new PathPatternParser();
    }

    @Bean
    public Map<PathPattern, List<String>> pathPatterns(SecurityProperties properties, PathPatternParser parser) {
        return properties.getWhiteList()
                .stream()
                .collect(
                        Collectors.toMap(
                                openedRoute -> parser.parse(openedRoute.getUrl()),
                                openedRoute -> Arrays.stream(openedRoute.getMethods().split(","))
                                        .map(String::trim)
                                        .toList()
                        )
                );
    }
}