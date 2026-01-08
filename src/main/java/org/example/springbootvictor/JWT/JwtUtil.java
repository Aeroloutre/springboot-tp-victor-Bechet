package org.example.springbootvictor.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // secret key pour signer les tokens
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // J'ai mis validité 1 an comme ça pas besoin de le renégocier souvent
    private final long JWT_TOKEN_VALIDITY = 365 * 24 * 60 * 60 * 1000;

    // Extraire le username du token
    public String extractUsername(final String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    // Extraire la date d'expiration
    public Date extractExpiration(final String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    // Extraire une information spécifique du token
    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire toutes les informations du token
    private Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token est expiré
    private Boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }

    // Générer un token pour un utilisateur
    public String generateToken(final String username, final String role) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return this.createToken(claims, username);
    }

    // Créer le token JWT
    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.JWT_TOKEN_VALIDITY))
                .signWith(this.SECRET_KEY)
                .compact();
    }

    // Valider le token
    public Boolean validateToken(final String token, final String username) {
        String extractedUsername = this.extractUsername(token);
        return (extractedUsername.equals(username) && !this.isTokenExpired(token));
    }

    // Extraire le rôle du token
    public String extractRole(final String token) {
        return this.extractAllClaims(token).get("role", String.class);
    }
}