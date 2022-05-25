package com.javachallenge.fileapi.dataAccess;

import com.javachallenge.fileapi.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select case when count(u)> 0 then true else false end " +
            "from User u where lower(u.email) like lower(:email)")
    boolean existsByEmailCustomQuery(@Param(("email")) String email);
    boolean existsByEmail(String email);

}
