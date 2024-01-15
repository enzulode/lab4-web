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
        //        @formatter:off
        super(
            username,
            password,
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities
        );
//        @formatter:on
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
        //        @formatter:off
        return new AuthorizedUserBuilder(
                username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
//        @formatter:on
    }

    /**
     * User email getter.
     *
     * @return user email
     */
    public String getEmail() {
        return this.getUsername();
    }

    /** This class enhances {@link AuthorizedUser} construction process. */
    public static class AuthorizedUserBuilder {

        /** Building instance. */
        private final AuthorizedUser entity;

        /**
         * Constructs authorized user from provided parameters.
         *
         * @param username username
         * @param password password
         * @param authorities a collection of authorities
         */
        AuthorizedUserBuilder(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
            //            @formatter:off
            if (password == null) password = "";
            this.entity = new AuthorizedUser(username, password, authorities);
//            @formatter:on
        }

        /**
         * Constructs authorized user from provided parameters.
         *
         * @param username username
         * @param password password
         * @param enabled is the user enabled
         * @param accountNonExpired is the user account non expired
         * @param credentialsNonExpired are the user credentials non expired
         * @param accountNonLocked is the user account not locked
         * @param authorities a collection of authorities
         */
        AuthorizedUserBuilder(
                String username,
                String password,
                boolean enabled,
                boolean accountNonExpired,
                boolean credentialsNonExpired,
                boolean accountNonLocked,
                Collection<? extends GrantedAuthority> authorities) {
            //            @formatter:off
            if (password == null) password = "";
            this.entity = new AuthorizedUser(
                username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
            );
//            @formatter:on
        }

        /**
         * User id setter.
         *
         * @param id user id
         * @return builder instance
         */
        public AuthorizedUserBuilder id(UUID id) {
            this.entity.setId(id);
            return this;
        }

        /**
         * User firstname setter
         *
         * @param firstName user firstname
         * @return builder instance
         */
        public AuthorizedUserBuilder firstName(String firstName) {
            this.entity.setFirstName(firstName);
            return this;
        }

        /**
         * User lastname setter
         *
         * @param lastName user firstname
         * @return builder instance
         */
        public AuthorizedUserBuilder lastName(String lastName) {
            this.entity.setLastName(lastName);
            return this;
        }

        /**
         * User middle name setter
         *
         * @param middleName user firstname
         * @return builder instance
         */
        public AuthorizedUserBuilder middleName(String middleName) {
            this.entity.setMiddleName(middleName);
            return this;
        }

        /**
         * User profile image setter.
         *
         * @param profileImg user profile image
         * @return builder instance
         */
        public AuthorizedUserBuilder profileImg(String profileImg) {
            this.entity.setProfileImg(profileImg);
            return this;
        }

        /**
         * Constructs the user.
         *
         * @return user instance
         */
        public AuthorizedUser build() {
            return this.entity;
        }
    }
}
