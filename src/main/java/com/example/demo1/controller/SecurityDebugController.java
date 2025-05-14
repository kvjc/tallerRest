package com.example.demo1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SecurityDebugController {

    @GetMapping("/debug-auth")
    public Map<String, Object> debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> authInfo = new HashMap<>();
        
        authInfo.put("principal", auth.getPrincipal().toString());
        authInfo.put("name", auth.getName());
        authInfo.put("authorities", auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        authInfo.put("authenticated", auth.isAuthenticated());
        authInfo.put("details", auth.getDetails() != null ? auth.getDetails().toString() : null);
        
        return authInfo;
    }
}