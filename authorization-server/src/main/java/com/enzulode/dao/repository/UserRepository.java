package com.enzulode.dao.repository;

import com.enzulode.dao.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.InitializingBean;

/** UserEntity repository abstraction. */
public interface UserRepository extends InitializingBean {

    /**
     * This method saves user.
     *
     * @param userEntity user to be saved
     * @return saved user entity instance
     */
    UserEntity save(UserEntity userEntity);

    /**
     * This method finds user by id.
     *
     * @param id user id
     * @return optional user instance
     */
    Optional<UserEntity> findById(UUID id);

    /**
     * This method finds user by email.
     *
     * @param email user email
     * @return optional user instance
     */
    Optional<UserEntity> findByEmail(String email);
}