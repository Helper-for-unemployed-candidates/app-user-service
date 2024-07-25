package com.jobhunter.appuserservice.config;

import com.jobhunter.appuserservice.entities.User;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(
                new SecurityRequirement().addList("Bearer Authentication")
        ).components(
                new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())
        ).info(
                new Info()
                        .title("user-service API")
                        .description("This api was generated using springdoc for user-service app")
                        .version("1.0")
                        .contact(
                                new Contact()
                                        .name("Dilshod Latipov")
                                        .email("dilshodlatipov748@gmail.com")
                        ).license(
                                new License()
                                        .name("Licence of API")
                                        .url("API licence url")
                        )
        );
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return Optional.of(user.getId());
            }
            return Optional.empty();
        };
    }
}
