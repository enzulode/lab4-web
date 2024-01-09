package com.enzulode.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/** This class contains OAuth2 Authorization Server configurations. */
@Configuration
public class AuthorizationServerConfig {

    /**
     * This method configures the default oauth2 authorization server filter chain.
     *
     * @param security Spring {@link HttpSecurity} instance
     * @return Spring {@link SecurityFilterChain} instance
     * @throws Exception if default filter chain settings applying failed
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity security)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(security);
        security.exceptionHandling(
                ex -> ex.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
        return security.build();
    }
}
