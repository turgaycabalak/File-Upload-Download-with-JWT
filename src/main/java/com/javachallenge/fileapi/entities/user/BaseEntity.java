package com.javachallenge.fileapi.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time",nullable = false)
    private LocalDateTime createdTime;



    public BaseEntity(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

}
