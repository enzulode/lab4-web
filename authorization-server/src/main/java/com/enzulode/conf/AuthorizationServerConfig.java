package com.enzulode.conf;

import com.enzulode.conf.security.SecurityConfig;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
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
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity security)
            throws Exception {
        // @formatter:off
        // apply default oauth2 security configurations
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(security);

        // enable OIDC, token generator, token and token introspection endpoints
        security.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

        // authentication entrypoint & jwt-based authentication configurations
        security
            .exceptionHandling(
                exHandlingCustomizer -> exHandlingCustomizer.authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint(SecurityConfig.LOGIN_PAGE_URL)
                )
            )
            .oauth2ResourceServer(
                resourceServerCustomizer -> resourceServerCustomizer.jwt(Customizer.withDefaults())
            );
        // @formatter:on

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
        // @formatter:off
        TokenSettings tokenSettings = TokenSettings.builder()
            .accessTokenTimeToLive(Duration.of(5, ChronoUnit.MINUTES))
            .refreshTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
            .reuseRefreshTokens(false)
            .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
            .build();

        RegisteredClient developmentClient = RegisteredClient
            .withId(UUID.randomUUID().toString())
            .clientId("dev-client")
            .clientSecret("{noop}dev")
            .redirectUri("http://localhost:8080/login/oauth2/code/dev-client")
            .postLogoutRedirectUri("http://localhost:8080/logged-out")
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
        // @formatter:on

        return new InMemoryRegisteredClientRepository(developmentClient);
    }

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
