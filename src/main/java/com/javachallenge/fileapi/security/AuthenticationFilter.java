package com.javachallenge.fileapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;


@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    // Trigger when we issue POST request to /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            // Grab credentials - username, password -> UserLoginReq
            UserLoginReq userLoginReq = new ObjectMapper().readValue(request.getInputStream(), UserLoginReq.class);
            log.info("Username is: {}",userLoginReq.getUsername());
            log.info("Password is: {}",userLoginReq.getPassword());

            // Credentials (Input)
            Authentication authenticationCredentials = new UsernamePasswordAuthenticationToken(
                    userLoginReq.getUsername(),
                    userLoginReq.getPassword()
            );

            // Principles (Output)
            Authentication authenticatePrinciples = authenticationManager.authenticate(authenticationCredentials);

            return authenticatePrinciples;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(jwtConfig.getAuthoritiesTag(), authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(Long.parseLong(jwtConfig.getTokenExpiration()))))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();

        response.addHeader(jwtConfig.getHeaderString(), jwtConfig.getTokenPrefix() + token);
    }

}
