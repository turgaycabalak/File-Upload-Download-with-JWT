package com.javachallenge.fileapi.security;

import com.javachallenge.fileapi.dataAccess.UserRepository;
import com.javachallenge.fileapi.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository("dao-service-v1")
public class UserPrincipalDaoService implements UserPrincipalDao{

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

}
