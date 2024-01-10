package com.enzulode.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/** This class contains security policy configuration for the development resource server. */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class ResourceServerConfig {

    /** Resource server configuration properties. */
    private final OAuth2ResourceServerProperties properties;

    /**
     * This method configures security filter chain for the resource server application.
     *
     * @param security Spring {@link HttpSecurity} instance
     * @return configured {@link SecurityFilterChain} instance
     * @throws Exception if configuration process fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/actuator/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .oauth2ResourceServer(
                        rs ->
                                rs.opaqueToken(
                                        opaque ->
                                                opaque.introspectionUri(
                                                                properties
                                                                        .getOpaquetoken()
                                                                        .getIntrospectionUri())
                                                        .introspectionClientCredentials(
                                                                properties
                                                                        .getOpaquetoken()
                                                                        .getClientId(),
                                                                properties
                                                                        .getOpaquetoken()
                                                                        .getClientSecret())))
                .csrf(AbstractHttpConfigurer::disable);

        return security.build();
    }
}
