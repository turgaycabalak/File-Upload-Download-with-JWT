package com.javachallenge.fileapi.security;

import com.javachallenge.fileapi.entities.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserPrincipalDao userPrincipalDao;

    @Autowired
    public UserPrincipalDetailsService(@Qualifier("dao-service-v1") UserPrincipalDao userPrincipalDao) {
        this.userPrincipalDao = userPrincipalDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userPrincipalDao.getUserByUsername(username)
                .orElseThrow(() -> {
                    log.error("Username not found: " + username);
                    throw new UsernameNotFoundException("Username not found: " + username);
                });

        return new UserPrincipal(
                user.getRole().getGrantedAuthorities(),
                user.getPassword(),
                user.getEmail(),
                true,
                true,
                true,
                user.isEnabled()
        );
    }

}
