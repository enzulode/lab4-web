package com.enzulode.mapper;

import com.enzulode.dao.entity.UserEntity;
import com.enzulode.dto.AuthorizedUser;
import java.util.Collections;

/** This class contains authorized user mapping utilities. */
public class AuthorizedUserMapper {

    /**
     * This method maps UserEntity instance to the Authorized user.
     *
     * @param entity user entity instance
     * @return authorized user instance
     */
    public static AuthorizedUser map(UserEntity entity) {
        return AuthorizedUser.builder(
                        entity.getEmail(), entity.getPasswordHash(), Collections.emptyList())
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .profileImg(entity.getProfileImg())
                .build();
    }
}
