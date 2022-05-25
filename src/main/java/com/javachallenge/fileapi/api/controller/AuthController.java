package com.javachallenge.fileapi.api.controller;

import com.javachallenge.fileapi.business.UserService;
import com.javachallenge.fileapi.dto.UserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@Valid @RequestBody UserRegistration userRegistration){
        userService.saveUser(userRegistration);
    }

}
