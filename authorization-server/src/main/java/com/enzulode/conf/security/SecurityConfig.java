package com.enzulode.conf.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/** This class contains security-related configurations for the authorization server. */
@Configuration
public class SecurityConfig {

    /** User details service instance. */
    private final UserDetailsService userDetailsService;

    /** This property defines an application login page url. */
    public static final String LOGIN_PAGE_URL = "/login";

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
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity security,
            @Qualifier("customCorsPolicyConfig") CorsConfigurationSource corsConfigSource)
            throws Exception {
        // @formatter:off
        security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(loginCustomizer -> loginCustomizer.loginPage(LOGIN_PAGE_URL))
            .csrf(AbstractHttpConfigurer::disable)
            .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigSource));

        security.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService);
        // @formatter:on
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
