package com.enzulode.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/** This class encapsulates all security-related configurations. */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /** Resource server configuration properties. */
    private final OAuth2ResourceServerProperties resourceServerProps;

    /** Custom authentication provider instance. */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Constructs resource server config instance with provided parameters.
     *
     * @param resourceServerProps resource server properties instance
     * @param authenticationProvider authentication provider instance
     */
    public SecurityConfig(
            OAuth2ResourceServerProperties resourceServerProps,
            @Qualifier("customAuthenticationProvider") AuthenticationProvider authenticationProvider) {
        this.resourceServerProps = resourceServerProps;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * This method configures security filter chain for the resource server application.
     *
     * @param security Spring {@link HttpSecurity} instance
     * @return configured {@link SecurityFilterChain} instance
     * @throws Exception if configuration process fails
     */
    @Bean
    public SecurityFilterChain configureSecurityFilterChain(
            HttpSecurity security,
            @Qualifier("customCorsPolicyConfig") CorsConfigurationSource corsConfigSource)
            throws Exception {
        // @formatter:off
        String jwtIssuerLocation = resourceServerProps.getJwt().getIssuerUri();
        security
            .authorizeHttpRequests(
                authorize -> authorize
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(
                resourceServer -> resourceServer.jwt(
                    jwtCustomization -> jwtCustomization.decoder(
                        JwtDecoders.fromIssuerLocation(jwtIssuerLocation)
                    )
                )
            )
            .authenticationProvider(authenticationProvider)
            .cors(corsCustomization -> corsCustomization.configurationSource(corsConfigSource));
        // @formatter:on
        return security.build();
    }
}
