package car.sale.authorizationservice.config;

import car.sale.authorizationservice.gateway.UserRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
//@RefreshScope
public class AppConfig {

    @Bean
    public UserRestClient userRestClient(
            @Value("${app.security.user-data-provider-url}")
            String baseUrl
    ) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(WebClient.builder()
                        .baseUrl(baseUrl)
                        .build()))
                .build()
                .createClient(UserRestClient.class);
    }
}
