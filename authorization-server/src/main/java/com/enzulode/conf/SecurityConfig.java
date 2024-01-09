package com.enzulode.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** This class contains security-related configurations for the authorization server. */
@Configuration
public class SecurityConfig {

    /**
     * This method configures security policy for the whole application.
     *
     * @param security Spring {@link HttpSecurity} instance
     * @return configured {@link SecurityFilterChain} instance
     * @throws Exception if configuration goes wrong
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        return security.formLogin(Customizer.withDefaults()).build();
    }
}
