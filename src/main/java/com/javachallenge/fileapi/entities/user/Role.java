package com.javachallenge.fileapi.entities.user;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.javachallenge.fileapi.entities.user.Permission.*;


@AllArgsConstructor
@Getter
public enum Role {
    USER(Sets.newHashSet()), //default
    ADMIN(Sets.newHashSet(USER_READ,USER_WRITE,ADMIN_READ)), //admin manager CRUD
    SYSTEM_MANAGER(Sets.newHashSet(USER_READ,USER_WRITE,ADMIN_READ,ADMIN_WRITE)); // internal operations

    private final Set<Permission> permissions;


    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }

}
