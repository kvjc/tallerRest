package com.example.demo1.security;

import com.example.demo1.model.Permission;
import org.springframework.security.core.GrantedAuthority;

public class SecurityAuthority implements GrantedAuthority {

    private final Permission permission;

    public SecurityAuthority(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission.getName();
    }
}
