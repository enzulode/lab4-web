package com.enzulode.dao.repository;

import com.enzulode.dao.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** UserEntity repository abstraction. */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * This method finds user by email.
     *
     * @param email user email
     * @return optional user instance
     */
    Optional<UserEntity> findByEmail(String email);
}
