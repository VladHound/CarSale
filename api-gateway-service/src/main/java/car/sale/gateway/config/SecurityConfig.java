package car.sale.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${services.frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        http.csrf().disable()
                .cors().disable()
//                .cors().configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
////                    configuration.setAllowedOrigins(List.of(frontendUrl));
//                    configuration.setAllowedOrigins(List.of("http://127.0.0.1:3000"));
//                    configuration.setAllowedOrigins(List.of("http://192.168.0.90:3000"));
//                    configuration.setAllowedMethods(List.of("*"));
//                    configuration.setAllowedHeaders(List.of("*"));
//                    return configuration;
//                }).and()
                .authorizeExchange()
                .pathMatchers("/public/api/v1/mail/send/activation/**").permitAll()
                .pathMatchers("/public/api/v1/users/send/activation/**").permitAll()
                .anyExchange()
                .permitAll();

        return http.formLogin().disable().httpBasic().disable().build();
    }
}