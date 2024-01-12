package com.enzulode.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

/** This class contains utility configurations. */
@Configuration
public class ConfigUtilities {

    /**
     * This method instantiates OAuth2 in-memory authorization service.
     *
     * @return OAuth2 authorization service instance
     */
    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }
}
