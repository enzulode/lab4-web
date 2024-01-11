package com.enzulode.dto;

import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/** This class represent authenticated user. */
@Getter
@Setter
public class AuthorizedUser extends User {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;

    public AuthorizedUser(
            String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthorizedUser(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(
                username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
    }

    public static AuthorizedUserBuilder builder(
            String username, String password, Collection<? extends GrantedAuthority> authorities) {
        return new AuthorizedUserBuilder(username, password, authorities);
    }

    public static AuthorizedUserBuilder builder(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        return new AuthorizedUserBuilder(
                username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
    }

    public String getEmail() {
        return this.getUsername();
    }

    public static class AuthorizedUserBuilder {

        private final AuthorizedUser entity;

        AuthorizedUserBuilder(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
            if (password == null) password = "";

            this.entity = new AuthorizedUser(username, password, authorities);
        }

        AuthorizedUserBuilder(
                String username,
                String password,
                boolean enabled,
                boolean accountNonExpired,
                boolean credentialsNonExpired,
                boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities) {
            this.entity =
                    new AuthorizedUser(
                            username,
                            password,
                            enabled,
                            accountNonExpired,
                            credentialsNonExpired,
                            accountNonLocked,
                            authorities);
        }

        public AuthorizedUserBuilder id(UUID id) {
            this.entity.setId(id);
            return this;
        }

        public AuthorizedUserBuilder firstName(String firstName) {
            this.entity.setFirstName(firstName);
            return this;
        }

        public AuthorizedUserBuilder lastName(String lastName) {
            this.entity.setLastName(lastName);
            return this;
        }

        public AuthorizedUserBuilder middleName(String middleName) {
            this.entity.setMiddleName(middleName);
            return this;
        }

        public AuthorizedUserBuilder profileImg(String profileImg) {
            this.entity.setProfileImg(profileImg);
            return this;
        }

        public AuthorizedUser build() {
            return this.entity;
        }
    }
}
