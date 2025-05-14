package com.example.demo1.security;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Permission;
import com.example.demo1.model.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUser implements UserDetails {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUser.class);
    private final AppUser user;
    
    public SecurityUser(AppUser user) { 
        this.user = user; 
    }

    @Override public String getUsername() { return user.getName(); }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        // Añadir roles con el prefijo ROLE_
        for (Role role : user.getRoles()) {
            String roleName = role.getName();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            authorities.add(authority);
            logger.debug("Añadiendo autoridad de rol: {}", roleName);
        }
        
        // Añadir permisos
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getName());
                authorities.add(authority);
                logger.debug("Añadiendo autoridad de permiso: {}", permission.getName());
            }
        }
        
        // Registrar todas las autoridades
        logger.info("Usuario {} tiene las siguientes autoridades: {}", user.getName(), authorities);
        
        return authorities;
    }
}