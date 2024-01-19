package com.enzulode.dto;

import com.enzulode.security.authentication.CustomAuthentication;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/** This class represent authenticated user. */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AuthorizedUser implements CustomAuthentication {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Object details;

    private boolean authenticated = false;

    /**
     * Constructs user instance from another dto.
     *
     * @param principal user info dto instance
     * @return authorized user dto instance or null
     */
    public static AuthorizedUser build(UserInfo principal) {
        if (principal == null) return null;

        List<GrantedAuthority> authorities = Collections.emptyList();
        if (principal.getAuthorities() != null) {
            //            @formatter:off
            authorities = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
//            @formatter:on
        }

        //        @formatter:off
        return AuthorizedUser.builder()
            .id(principal.getId())
            .firstName(principal.getFirstName())
            .lastName(principal.getLastName())
            .middleName(principal.getMiddleName())
            .profileImg(principal.getProfileImg())
            .email(principal.getEmail())
            .authorities(authorities)
            .build();
//        @formatter:on
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public String getPrincipal() {
        return email;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public String getName() {
        return email;
    }
}
