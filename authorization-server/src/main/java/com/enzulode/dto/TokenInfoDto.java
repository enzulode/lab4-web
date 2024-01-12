package com.enzulode.dto;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** This class represents introspection response data. */
@Getter
@Setter
@Builder
public class TokenInfoDto {

    private Boolean active;
    private String sub;
    private List<String> aud;
    private Instant nbf;
    private List<String> scopes;
    private URL iss;
    private Instant exp;
    private Instant iat;
    private String jti;
    private String clientId;
    private String tokenType;

    private IntrospectionPrincipalDto principal;
}
