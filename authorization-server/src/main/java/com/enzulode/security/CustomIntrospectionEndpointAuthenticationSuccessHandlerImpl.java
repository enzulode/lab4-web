package com.enzulode.security;

import com.enzulode.dto.AuthorizedUser;
import com.enzulode.dto.IntrospectionPrincipalDto;
import com.enzulode.dto.TokenInfoDto;
import com.enzulode.dto.TokenInfoDto.TokenInfoDtoBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenIntrospection;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2TokenIntrospectionAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/** Custom introspection endpoint authentication success handler implementation. */
@Component
@RequiredArgsConstructor
public class CustomIntrospectionEndpointAuthenticationSuccessHandlerImpl
        implements AuthenticationSuccessHandler {

    /** Allowed principal classname. */
    private static final String principalAttributeKey = "java.security.Principal";

    /** Jackson http message converter instance. */
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    /** OAuth2 authorization service instance. */
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    /**
     * This method handles successful authentication.
     *
     * @param req http request instance
     * @param resp http response instance
     * @param auth authentication instance
     * @throws IOException if something goes wrong during jackson writing
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest req, HttpServletResponse resp, Authentication auth)
            throws IOException {
        OAuth2TokenIntrospectionAuthenticationToken introspectionAuthToken =
                (OAuth2TokenIntrospectionAuthenticationToken) auth;
        TokenInfoDtoBuilder tokenInfoDtoBuilder = TokenInfoDto.builder().active(false);

        if (introspectionAuthToken.getTokenClaims().isActive()) {
            OAuth2TokenIntrospection claims = introspectionAuthToken.getTokenClaims();
            applyClaims.accept(tokenInfoDtoBuilder, claims);

            //            check if oauth2 authorization is null - then just do nothing
            String token = introspectionAuthToken.getToken();
            OAuth2Authorization tokenAuth =
                    oAuth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
            if (tokenAuth == null) return;

            //            check if principal is null - then just do nothing
            Authentication attributeAuth = tokenAuth.getAttribute(principalAttributeKey);
            if (attributeAuth == null) return;

            if (!(attributeAuth.getPrincipal() instanceof AuthorizedUser authorizedUser)) {
                String unsupportedPrincipalClassName =
                        attributeAuth.getPrincipal().getClass().getSimpleName();
                throw new RuntimeException(
                        "Principal class = %s is not supported"
                                .formatted(unsupportedPrincipalClassName));
            }

            //            apply scopes from granted authorities
            String scopePrefix = "SCOPE_";
            tokenInfoDtoBuilder.scopes(
                    authorizedUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .filter(authority -> authority.startsWith(scopePrefix))
                            .map(scope -> scope.substring(scopePrefix.length()))
                            .collect(Collectors.toList()));
            //            seems that authentication succeed - apply principal to the principal dto
            tokenInfoDtoBuilder.principal(IntrospectionPrincipalDto.build(authorizedUser));
        }

        //        just write the response
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(resp);
        mappingJackson2HttpMessageConverter.write(tokenInfoDtoBuilder.build(), null, httpResponse);
    }

    /**
     * This binary consumer applies claims from {@link OAuth2TokenIntrospection} to {@link
     * TokenInfoDto} via {@link TokenInfoDtoBuilder} instance.
     */
    private final BiConsumer<TokenInfoDtoBuilder, OAuth2TokenIntrospection> applyClaims =
            (builder, claims) -> {
                builder.active(claims.isActive())
                        .sub(claims.getSubject())
                        .aud(claims.getAudience())
                        .nbf(claims.getNotBefore())
                        .scopes(
                                (claims.getScopes() == null || claims.getScopes().isEmpty())
                                        ? Collections.emptyList()
                                        : claims.getScopes())
                        .iss(claims.getIssuer())
                        .exp(claims.getExpiresAt())
                        .iat(claims.getIssuedAt())
                        .jti(claims.getId())
                        .clientId(claims.getClientId())
                        .tokenType(claims.getTokenType());
            };
}
