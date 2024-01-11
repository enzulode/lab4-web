package com.enzulode.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/error")
                                        .permitAll()
                                        .requestMatchers("/actuator/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(Customizer.withDefaults());
        return security.build();
    }
}
