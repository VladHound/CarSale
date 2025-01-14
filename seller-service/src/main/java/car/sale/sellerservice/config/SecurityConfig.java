package car.sale.sellerservice.config;

import car.sale.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public PermissionService permissionService() {
        return new PermissionService();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/public/api/v1/users/signup")
                .requestMatchers("/private/api/v1/users/auth-details/**")
                .requestMatchers("/actuator/**")
                .requestMatchers("/v3/api-docs/**")
                .requestMatchers("/swagger-ui*/**")
                .requestMatchers("/swagger-resources/**")
                .requestMatchers("/webjars/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/public/api/v1/sellers").permitAll()
                    .requestMatchers(HttpMethod.GET, "/private/api/v1/sellers/auth").permitAll()
                    .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt().decoder(jwtDecoder()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(jwkSetUri)
                .jwsAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }
}