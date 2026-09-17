package com.henrikel.libraryapi.core.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.GetMapping;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
@Getter
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final long EXPIRACAO_MS = 2 * 60 * 60 * 1000;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String gerarToken(UserDetails usuario){
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + EXPIRACAO_MS);
        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getSigningKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            Date expiracao = extrairClaims(token).getExpiration();
            return expiracao.after(new Date());
        } catch (Exception e){
            return false;
        }
    }
    private Claims extrairClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
