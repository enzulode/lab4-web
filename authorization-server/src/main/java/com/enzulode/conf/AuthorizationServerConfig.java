package com.enzulode.conf;

import com.enzulode.model.KeysConfiguration;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
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

        //        apply default oauth2 security configurations
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(security);

        //        enable oidc
        security.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        //        configure authentication entrypoint and enable jwt-based authentication for
        // resource server
        security.exceptionHandling(
                        ex ->
                                ex.authenticationEntryPoint(
                                        new LoginUrlAuthenticationEntryPoint("/login")))
                .oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));

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
                        .accessTokenTimeToLive(Duration.of(5, ChronoUnit.MINUTES))
                        .refreshTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
                        .reuseRefreshTokens(false)
                        .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                        .build();

        RegisteredClient developmentClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
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

        return new InMemoryRegisteredClientRepository(developmentClient);
    }

    /**
     * Configure development jwk source.
     *
     * @param keys keys configuration properties holder instance
     * @return development-ready jwk source instance
     */
    @Bean
    @Profile("dev")
    public JWKSource<SecurityContext> jwkSource(KeysConfiguration keys) {
        List<JWK> rsaKeys =
                keys.getRsa().stream()
                        .map(
                                keyPair ->
                                        new RSAKey.Builder(keyPair.getPublicKey())
                                                .keyID(keyPair.getId())
                                                .privateKey(keyPair.getPrivateKey())
                                                .build())
                        .collect(Collectors.toList());

        JWKSet jwkSet = new JWKSet(rsaKeys);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * Configure jwt decoder as a jwk-source-based jwt decoder.
     *
     * @param jwkSource jwk source instance
     * @return configured jwt decoder instance
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Customizing jwt and id-token contents with list of authorities.
     *
     * @return configured oauth2 token customizer instance
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return ctx -> {
            Authentication principal = ctx.getPrincipal();

            //            customize jwt access token
            if (OAuth2TokenType.ACCESS_TOKEN.equals(ctx.getTokenType())) {
                Set<String> authorities =
                        principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet());

                ctx.getClaims().claim("authorities", authorities);
            }

            //            customize oidc id-token
            if (OidcParameterNames.ID_TOKEN.equals(ctx.getTokenType().getValue())) {
                Set<String> authorities =
                        principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet());
                ctx.getClaims().claim("authorities", authorities);
            }
        };
    }
}
