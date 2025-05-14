package com.example.demo1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    private String secret = "tu_clave_secreta_muy_larga_y_segura_para_firmar_jwt_tokens_al_menos_32_caracteres";
    private long expiration = 3600000; // 1 hora en milisegundos
    private String issuer = "example-application";
    private String header = "Authorization";
    private String prefix = "Bearer ";
}