package labs.pm.userservice.security;


import org.springframework.boot.context.properties.ConfigurationProperties;

//classe qui permet de lire automatiquement les valeurs de application.yml et les mettre dans un objet Java.
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        long expirationMinutes
) {
}
