package com.enzulode.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/** This class contains security-related configurations for the authorization server. */
@Configuration
public class SecurityConfig {

    /** User details service instance. */
    private final UserDetailsService userDetailsService;

    /**
     * Constructs security configuration from provided parameters.
     *
     * @param userDetailsService user details instance
     */
    public SecurityConfig(
            @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
        //        @formatter:off
        security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());

        security.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService);
//        @formatter:on
        return security.build();
    }

    /**
     * Configure application password encoder instance using password encoder factories.
     *
     * @return delegating password encoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
