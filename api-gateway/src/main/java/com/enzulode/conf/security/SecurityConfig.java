package com.enzulode.conf.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

/** Security policy configurations for the api gateway application. */
@Configuration
public class SecurityConfig {

    /**
     * Configure webflux security filter chain {@link SecurityWebFilterChain} instance for incoming
     * requests authentication. Allows actuator-related endpoints to be accessed without
     * authentication. Other requests are authorized.
     *
     * @param security webflux http security as instance of {@link ServerHttpSecurity}
     * @return configured {@link SecurityWebFilterChain} instance
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity security,
            @Qualifier("customReactiveCorsPolicyConfig") CorsConfigurationSource corsConfigSource) {
        // @formatter:off
        security
            .authorizeExchange(exc -> exc
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .oauth2Client(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .cors(
                c -> c.configurationSource(corsConfigSource)
            );
        // @formatter:on

        return security.build();
    }
}
