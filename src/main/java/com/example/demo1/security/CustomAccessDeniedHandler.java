package com.example.demo1.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("Usuario: {} intentó acceder a un recurso protegido: {}", 
                     auth.getName(), request.getRequestURI());
            logger.warn("Autoridades del usuario: {}", auth.getAuthorities());
        }

        logger.error("Acceso denegado: {}", accessDeniedException.getMessage());
        
        // Redirigir a una página de acceso denegado personalizada
        response.sendRedirect(request.getContextPath() + "/access-denied");
    }
}