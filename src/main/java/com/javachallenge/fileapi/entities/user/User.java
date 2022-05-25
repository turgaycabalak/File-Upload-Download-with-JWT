package com.javachallenge.fileapi.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false,length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    private boolean isEnabled = true;



    public User(LocalDateTime createdTime,
                String email,
                String password,
                Role role) {

        super(createdTime);
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
