package com.javachallenge.fileapi.security;


import com.javachallenge.fileapi.entities.user.User;

import java.util.Optional;

public interface UserPrincipalDao {

    Optional<User> getUserByUsername(String username);

}
