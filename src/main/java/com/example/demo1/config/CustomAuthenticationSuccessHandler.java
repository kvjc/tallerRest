package com.example.demo1.config;

import com.example.demo1.security.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Autowired
    public CustomAuthenticationSuccessHandler(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        setDefaultTargetUrl("/user/dashboard");  // Redirige después del login si es necesario
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException, IOException {
        // Generar el JWT
        String jwt = jwtProvider.generateToken(authentication);

        // Agregar el JWT al encabezado "Authorization"
        response.setHeader("Authorization", "Bearer " + jwt);

        // Devolver el JWT en el cuerpo de la respuesta como JSON
        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\"" + jwt + "\"}");

        // No redirigir
    }
}

