package com.enzulode.conf.security;

import com.enzulode.conf.model.CorsConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/** This class is responsible for reactive CORS policy-related stuff. */
@Configuration
public class ReactiveCorsPolicyConfig {

    /**
     * This method configures reactive CORS policy for this microservice.
     *
     * @param props CORS configuration properties
     * @return CORS configuration source instance
     */
    @Bean("customReactiveCorsPolicyConfig")
    public CorsConfigurationSource configureCorsConfigurationSource(
            CorsConfigurationProperties props) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // @formatter:off
        props.getConfigs().forEach(
            prop -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOriginPatterns(prop.allowedOriginPatterns());
                config.setAllowCredentials(prop.allowCredentials());
                config.setAllowedMethods(prop.allowedMethods());
                config.setAllowedHeaders(prop.allowedHeaders());
                config.setMaxAge(prop.maxAge());

                source.registerCorsConfiguration(prop.mappingPattern(), config);
            }
        );
        // formatter:on

        return source;
    }
}
