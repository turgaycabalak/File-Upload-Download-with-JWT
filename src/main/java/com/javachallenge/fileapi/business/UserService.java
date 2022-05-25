package com.javachallenge.fileapi.business;

import com.javachallenge.fileapi.dataAccess.UserRepository;
import com.javachallenge.fileapi.dto.UserRegistration;
import com.javachallenge.fileapi.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.javachallenge.fileapi.entities.user.Role.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void saveUser(UserRegistration userRegistration) {
        User userWillSaveDb = new User(
                LocalDateTime.now(),
                userRegistration.email(),
                passwordEncoder.encode(userRegistration.password()),
                USER
        );
        userRepository.save(userWillSaveDb);
    }

}
