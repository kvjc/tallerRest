package com.example.demo1.service;

import com.example.demo1.model.AppUser;
import com.example.demo1.model.Permission;
import com.example.demo1.model.Role;
import com.example.demo1.service.impl.AppUserServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service  // Solo aquí la anotación: no definimos beans de seguridad
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserServiceImpl userRepository;

    public CustomUserDetailsService(AppUserServiceImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser u = userRepository.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: " + username)
            );

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // ➤ Agregamos cada rol (ya llevan el prefijo ROLE_ en la BD)
        for (Role r : u.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        // ➤ Agregamos los permisos
        for (Role r : u.getRoles()) {
            for (Permission p : r.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(p.getName()));
            }
        }

        // Retornamos el User de Spring Security con roles y permisos
        return new User(u.getName(), u.getPassword(), authorities);
    }
}
