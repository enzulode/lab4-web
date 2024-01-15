package com.enzulode.conf;

import com.enzulode.dto.AuthorizedUser;
import com.enzulode.dto.IntrospectionPrincipalDto;
import com.enzulode.model.KeysConfiguration;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/** This class contains jwt-related configurations. */
@Configuration
public class JwtConfig {

    /**
     * Configure development jwk source.
     *
     * @param keys keys configuration properties holder instance
     * @return development-ready jwk source instance
     */
    @Bean
    @Profile("dev")
    public JWKSource<SecurityContext> jwkSource(KeysConfiguration keys) {
        //        @formatter:off
        List<JWK> rsaKeys = keys.getRsa().stream()
            .map(
                keyPair -> new RSAKey.Builder(keyPair.getPublicKey())
                    .keyID(keyPair.getId())
                    .privateKey(keyPair.getPrivateKey())
                    .build()
            )
            .collect(Collectors.toList());
//        @formatter:on

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
     * Configure token customizer.
     *
     * @return token customizer instance
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        //        @formatter:off
        String principalKey = "java.security.Principal";
        return (ctx) -> {
            if (ctx.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                OAuth2Authorization authorization = ctx.getAuthorization();
                if (authorization == null) return;

                Authentication attributeAuth = authorization.getAttribute(principalKey);
                if (attributeAuth == null) return;

                if (!(attributeAuth.getPrincipal() instanceof AuthorizedUser authorizedUser)) return;
                ctx.getClaims()
                    .claim("principal", IntrospectionPrincipalDto.build(authorizedUser));
            }
        };
//        @formatter:on
    }
}
