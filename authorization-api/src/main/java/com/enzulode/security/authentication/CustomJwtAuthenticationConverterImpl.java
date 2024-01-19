package com.enzulode.security.authentication;

import com.enzulode.dto.AuthorizedUser;
import com.enzulode.dto.UserInfo;
import java.util.Collection;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/** Custom implementation of the Converter abstraction. */
@Slf4j
public class CustomJwtAuthenticationConverterImpl implements Converter<Jwt, CustomAuthentication> {

    /** Granted authorities converter instance. */
    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    /** Principal info claim name. */
    private final String principalInfoClaimName = "principal";

    /** {@inheritDoc} */
    @Override
    public CustomAuthentication convert(@NonNull Jwt jwt) {
        //        TODO: remove Map<String, Object> to UserInfo conversion from the UserInfo class
        //        TODO: implement specific converter for Map -> UserInfo
        return AuthorizedUser.build(UserInfo.fromMap(jwt.getClaimAsMap(principalInfoClaimName)));
    }
}
