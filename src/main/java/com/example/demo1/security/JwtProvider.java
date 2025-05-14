package com.example.demo1.security;

import com.example.demo1.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private JwtConfig jwtConfig;

    // Construye la clave secreta a partir de jwtConfig.getSecret()
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Genera un JWT firmado con HS256
    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtConfig.getExpiration());

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida firma y expiración (jjwt 0.12.6)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()                        // reemplaza parserBuilder() :contentReference[oaicite:0]{index=0}
                    .verifyWith(getSigningKey())      // usa verifyWith en 0.12.x :contentReference[oaicite:1]{index=1}
                    .build()
                    .parseSignedClaims(token);        // parseSignedClaims devuelve SignedJwt :contentReference[oaicite:2]{index=2}
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.error("JWT inválido o expirado", ex);
            return false;
        }
    }

    // Extrae todos los claims del token
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();                       // getPayload() extrae Claims :contentReference[oaicite:3]{index=3}
    }

    // Obtiene el sujeto (username)
    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    // Obtiene la lista de authorities a partir del claim "roles"
    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        String roles = getAllClaims(token).get("roles", String.class);
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Construye el Authentication para Spring Security
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        Collection<GrantedAuthority> authorities = getAuthoritiesFromToken(token);
        UserDetails principal = new User(username, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
