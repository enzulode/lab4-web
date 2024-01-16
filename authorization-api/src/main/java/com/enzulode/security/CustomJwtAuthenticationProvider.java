package com.enzulode.security;

import com.enzulode.security.authentication.CustomAuthentication;
import com.enzulode.security.authentication.CustomJwtAuthenticationConverterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/** This class is a custom authentication provider over JWTs. */
@Component("customAuthenticationProvider")
@Slf4j
public class CustomJwtAuthenticationProvider implements AuthenticationProvider {

    /** Jwt decoder instance. */
    private final JwtDecoder jwtDecoder;

    /** Jwt to CustomAuthentication converter instance. */
    private Converter<Jwt, CustomAuthentication> jwtAuthenticationConverter =
            new CustomJwtAuthenticationConverterImpl();

    /**
     * This method constructs CustomAuthenticationProvider instance using provided parameters.
     *
     * @param jwtDecoder jwt decoder instance
     */
    public CustomJwtAuthenticationProvider(JwtDecoder jwtDecoder) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
    }

    /** {@inheritDoc} */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(bearer);
        CustomAuthentication customAuthentication = jwtAuthenticationConverter.convert(jwt);
        if (customAuthentication != null && customAuthentication.getDetails() == null)
            customAuthentication.setDetails(bearer.getDetails());
        if (customAuthentication != null) customAuthentication.setAuthenticated(true);
        return customAuthentication;
    }

    /**
     * This method translates bearer authentication token to jwt token.
     *
     * @param bearer bearer token authentication token instance
     * @return jwt
     */
    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        try {
            return jwtDecoder.decode(bearer.getToken());
        } catch (BadJwtException failed) {
            log.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Jwt authentication converter setter.
     *
     * @param jwtAuthenticationConverter jwt authentication converter instance
     */
    public void setJwtAuthenticationConverter(
            Converter<Jwt, CustomAuthentication> jwtAuthenticationConverter) {
        Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }
}
