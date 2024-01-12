package com.enzulode.dto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/** This class represents introspection response principal data. */
@Getter
@Builder
@AllArgsConstructor
public class IntrospectionPrincipalDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;
    private String email;
    private List<String> authorities;

    public static IntrospectionPrincipalDto build(AuthorizedUser authorizedUser) {
        if (authorizedUser == null) {
            return null;
        }

        List<String> authorities = Collections.emptyList();
        if (authorizedUser.getAuthorities() != null) {
            authorities =
                    authorizedUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());
        }

        return IntrospectionPrincipalDto.builder()
                .id(authorizedUser.getId())
                .firstName(authorizedUser.getFirstName())
                .lastName(authorizedUser.getLastName())
                .middleName(authorizedUser.getMiddleName())
                .profileImg(authorizedUser.getProfileImg())
                .email(authorizedUser.getEmail())
                .authorities(authorities)
                .build();
    }
}
