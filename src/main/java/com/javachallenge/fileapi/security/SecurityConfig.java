package com.javachallenge.fileapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserPrincipalDetailsService userPrincipalDetailsService;
    private final JwtConfig jwtConfig;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userPrincipalDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new AuthorizationFilter(jwtConfig), AuthenticationFilter.class)
                .authorizeRequests()

                .antMatchers("/login").permitAll()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers(POST,"/auth/signup").permitAll()

//                .antMatchers("/users/v1/getallusers").permitAll()
//                .antMatchers("/users/v1/getallusers2").permitAll()
//                .antMatchers("/users/v1/getuserbyid").permitAll()
//                .antMatchers("/users/v1/deleteuser/{id}").permitAll()

//                .antMatchers("/users").hasAnyRole(USER.name(), ADMIN.name(), SYSTEM_MANAGER.name())
//                .antMatchers("/admins").hasAnyRole(ADMIN.name(), SYSTEM_MANAGER.name())
//                .antMatchers("/systems").hasRole(SYSTEM_MANAGER.name())
//                .antMatchers(POST,"/systems").hasAuthority(ADMIN_WRITE.name())
//                .antMatchers(PUT,"/systems").hasAuthority(ADMIN_WRITE.name())
//                .antMatchers(DELETE,"/systems").hasAuthority(ADMIN_WRITE.name())

                .anyRequest().authenticated();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
