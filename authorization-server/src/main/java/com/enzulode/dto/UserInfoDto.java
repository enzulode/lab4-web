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
public class UserInfoDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;
    private String email;
    private List<String> authorities;

    /**
     * Constructs {@link UserInfoDto} using {@link AuthorizedUser} instance.
     *
     * @param authorizedUser authorized user instance
     * @return user principal dto
     */
    public static UserInfoDto build(AuthorizedUser authorizedUser) {
        if (authorizedUser == null) return null;

        //        @formatter:off
        List<String> authorities = Collections.emptyList();
        if (authorizedUser.getAuthorities() != null)
            authorities = authorizedUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return UserInfoDto.builder()
            .id(authorizedUser.getId())
            .firstName(authorizedUser.getFirstName())
            .lastName(authorizedUser.getLastName())
            .middleName(authorizedUser.getMiddleName())
            .profileImg(authorizedUser.getProfileImg())
            .email(authorizedUser.getEmail())
            .authorities(authorities)
            .build();
//        @formatter:on
    }
}
