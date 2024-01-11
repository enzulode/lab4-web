package com.enzulode.dao.repository;

import com.enzulode.dao.entity.UserEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/** Development implementation of user repository. */
@Repository
public class DevelopmentUserRepositoryImpl implements UserRepository {

    /** Temporary user storage. */
    private final Map<UUID, UserEntity> storage = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(UUID.randomUUID());
        }

        storage.put(userEntity.getId(), userEntity);
        return userEntity;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<UserEntity> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    /** {@inheritDoc} */
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return storage.values().stream().filter(it -> it.getEmail().equals(email)).findFirst();
    }

    /**
     * Enrich storage with development users.
     *
     * @throws Exception if something goes wrong
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        UserEntity user1 =
                UserEntity.builder()
                        .email("enzulode@gmail.com")
                        .passwordHash("{noop}dev")
                        .active(true)
                        .firstName("Ivan")
                        .lastName("Petrov")
                        .middleName("Sergeevich")
                        .build();

        UserEntity user2 =
                UserEntity.builder()
                        .email("admin@gmail.com")
                        .passwordHash("{noop}dev")
                        .active(true)
                        .firstName("Zykov")
                        .lastName("Dmitriy")
                        .middleName("Andreevich")
                        .build();

        save(user1);
        save(user2);
    }
}
