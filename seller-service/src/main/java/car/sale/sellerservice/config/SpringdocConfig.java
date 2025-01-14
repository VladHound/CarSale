package car.sale.sellerservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SpringdocConfig {

    @Bean
    public OpenAPI baseOpenApi() {
        return new OpenAPI().
                info(apiInfo());
    }

    private static Info apiInfo() {
        return new Info()
                .title("Spring Doc Open Api для работы с sellerservice")
                .version("1.0.0");
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/public/api/v1/**")
                .build();
    }
}
