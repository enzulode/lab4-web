package com.enzulode.service;

import com.enzulode.dao.entity.UserEntity;
import com.enzulode.dao.repository.UserRepository;
import com.enzulode.mapper.AuthorizedUserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for user loading process. Service loads user from the effective
 * storage.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    /** User repository instance. */
    private final UserRepository userRepository;

    /**
     * This method loads user by username from the effective storage.
     *
     * @param username user username
     * @return user instance
     * @throws UsernameNotFoundException if user was not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> entity = userRepository.findByEmail(username);
        if (entity.isEmpty())
            throw new UsernameNotFoundException("User with username = " + username + " not found");

        return AuthorizedUserMapper.map(entity.get());
    }
}
