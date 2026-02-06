package labs.pm.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

@Service
// @Service: Spring crée automatiquement un bean de cette classe
// Tu peux l’injecter dans AuthService, JwtFilter, etc.
public class JwtService {
//    @Value("${jwt.secret}")
    private final SecretKey key;         // clé de signature du JWT
//    @Value("${jwt.expiration-minutes}")
    private final long expirationMinutes; // durée de vie du token

    public JwtService(JwtProperties props){
        // Le constructeur reçoit JwtProperties (bean créé par Spring)
        // On transforme la chaîne secret en clé HMAC utilisable par JJWT
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = props.expirationMinutes();
    }

    public String generateToken(UserPrincipal principal) {
        // génère un token JWT signé
        Instant now = Instant.now(); // instant actuel
        Instant exp = now.plusSeconds(expirationMinutes * 60); // date d'expiration
        return Jwts.builder()
                .setSubject(principal.getUsername())       // subject = email (identifiant)
                .claim("uid", principal.getId())        // uid = userId (utile côté serveur)
                .claim("role", principal.getRole().name()) // role = rôle
                .setIssuedAt(Date.from(now))               // date de création
                .setExpiration(Date.from(exp))             // date d'expiration
                .signWith(key)                          // signature du token
                .compact();                             // retourne le token sous forme de String
    }

    public boolean isValid(String token) {
        // vérifie si le token est correct et pas expiré
        try {
            parseClaims(token); // si parsing ok, token valide
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // JwtException: signature invalide, token expiré, token mal formé, etc.
            return false;
        }
    }

    public String extractEmail(String token) {
        // récupère l’email (subject) depuis le token
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        // parse et vérifie la signature avec la clé "key"
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
