package car.sale.authorizationservice.config;

import car.sale.authorizationservice.gateway.SellerAuthClient;
import car.sale.authorizationservice.gateway.UserRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SellerAuthClientConfig {

    @Bean
    public SellerAuthClient sellerAuthClient() {
        return email -> null; // Вам нужно создать и настроить экземпляр SellerAuthClient
    }
}