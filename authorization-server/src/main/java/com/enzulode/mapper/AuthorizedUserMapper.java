package com.enzulode.mapper;

import com.enzulode.dao.entity.AuthorityEntity;
import com.enzulode.dao.entity.RoleEntity;
import com.enzulode.dao.entity.UserEntity;
import com.enzulode.dto.AuthorizedUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/** This class contains authorized user mapping utilities. */
public class AuthorizedUserMapper {

    /**
     * This method maps UserEntity instance to the Authorized user.
     *
     * @param entity user entity instance
     * @return authorized user instance
     */
    public static AuthorizedUser map(UserEntity entity) {
        //        @formatter:off
        List<GrantedAuthority> authorities = entity.getRoles().stream()
            .filter(RoleEntity::getActive)
            .flatMap(role -> role.getAuthorities().stream())
            .filter(AuthorityEntity::getActive)
            .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
            .collect(Collectors.toList());

        return AuthorizedUser.builder(entity.getEmail(), entity.getPasswordHash(), authorities)
            .id(entity.getId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .middleName(entity.getMiddleName())
            .profileImg(entity.getProfileImg())
            .build();
//        @formatter:on
    }
}
