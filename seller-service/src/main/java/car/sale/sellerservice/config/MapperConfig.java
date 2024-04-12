package car.sale.sellerservice.config;

import car.sale.sellerservice.mapper.SellerMapper;
import car.sale.sellerservice.mapper.SellerMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperConfig {

    @Bean
    @Primary
    public SellerMapper mapper() {
        return new SellerMapperImpl();
    }
}