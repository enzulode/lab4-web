package com.enzulode.conf;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
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

    /**
     * This method configures in-memory oauth2 authorization server clients repository with the
     * development client.
     *
     * @return {@link RegisteredClientRepository} instance with registered development client
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        TokenSettings tokenSettings =
                TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.of(5, ChronoUnit.MINUTES))
                        .refreshTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
                        .reuseRefreshTokens(false)
                        .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                        .build();

        RegisteredClient developmentClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("dev-client")
                        .clientSecret("{noop}dev")
                        .redirectUri("http://127.0.0.1:8080/login/oauth2/code/dev-client")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("user.read")
                        .scope("user.write")
                        .tokenSettings(tokenSettings)
                        .build();

        return new InMemoryRegisteredClientRepository(developmentClient);
    }
}
