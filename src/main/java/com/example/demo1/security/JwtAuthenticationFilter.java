package com.example.demo1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo1.config.JwtConfig;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        try {
            String jwt = getTokenFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                Authentication auth = jwtProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Usuario autenticado: {}", auth.getName());
            }
        } catch (Exception e) {
            logger.error("No se pudo establecer la autenticación del usuario", e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getPrefix())) {
            // Elimina el prefijo "Bearer " y luego limpia espacios/saltos de línea
            return bearerToken.substring(jwtConfig.getPrefix().length())
                    .replaceAll("\\s+", "")  // quita todos los espacios o saltos
                    .trim();                  // recorta espacios al inicio/final
        }
        return null;
    }

}
