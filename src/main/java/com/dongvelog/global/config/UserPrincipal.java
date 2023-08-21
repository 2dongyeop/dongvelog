package com.dongvelog.global.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    /*
    *
    * ROLE_ADMIN -> 역할
    * ADMIN -> 권한
    * */

    public UserPrincipal(com.dongvelog.domain.user.entity.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
