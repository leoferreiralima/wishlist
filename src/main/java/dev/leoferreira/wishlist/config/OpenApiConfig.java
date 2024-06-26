package dev.leoferreira.wishlist.config;

import dev.leoferreira.wishlist.infra.swagger.openapi.GenericErrorResponseCustomizer;
import dev.leoferreira.wishlist.infra.swagger.openapi.RefererServerBaseUrlCustomizer;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wishlist API")
                        .description("This is a sample Wishlist API built with spring boot, mongodb and redis")
                );
    }

    @Bean("genericErrorResponseCustomizer")
    public OpenApiCustomizer genericErrorResponseCustomizer() {
        return new GenericErrorResponseCustomizer();
    }

    @Bean("refererServerBaseUrlCustomizer")
    public RefererServerBaseUrlCustomizer refererServerBaseUrlCustomizer() {
        return new RefererServerBaseUrlCustomizer();
    }
}