package labs.pm.userservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import labs.pm.userservice.security.CustomUserDetailsService;
import labs.pm.userservice.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// @Component : Spring va créer automatiquement un objet (bean) de ce filtre au démarrage.
// Comme ça, on peut l’injecter dans la configuration Spring Security (SecurityConfig).
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter : ce filtre s’exécute 1 seule fois par requête HTTP.
    // Rôle global du filtre : si un JWT valide existe dans le header,
    // on "connecte" l’utilisateur pour cette requête (en mettant Authentication dans le SecurityContext).

    private final JwtService jwtService;
    // JwtService : classe utilitaire JWT.
    // - valide le token (signature + expiration)
    // - extrait des infos du token (ex: email)

    private final CustomUserDetailsService userDetailsService;
    // CustomUserDetailsService : classe qui sait charger un utilisateur depuis la base
    // à partir de son email (loadUserByUsername).

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        // Injection par constructeur (bonne pratique).
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // doFilterInternal : c’est la méthode appelée automatiquement à chaque requête.

        // ========== Étape 1 : Lire le header Authorization ==========
        // Le client doit envoyer : Authorization: Bearer <TOKEN>
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // ========== Étape 2 : Si pas de token, on laisse passer ==========
        // Important : ici on ne bloque pas la requête.
        // On continue la chaîne et c’est Spring Security qui décidera :
        // - endpoint public -> OK
        // - endpoint protégé -> 401 (car pas authentifié)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return; // on sort du filtre
        }

        // ========== Étape 3 : Extraire le token ==========
        // "Bearer " fait 7 caractères (6 lettres + espace)
        String token = header.substring(7);

        // ========== Étape 4 : Valider le token ==========
        // Si token invalide (expiré, signature fausse, format incorrect),
        // on ne "connecte" pas l’utilisateur.
        if (!jwtService.isValid(token)) {
            chain.doFilter(request, response);
            return;
        }

        // ========== Étape 5 : Extraire l’identité (email) depuis le token ==========
        // On utilise l’email comme username dans notre système.
        String email = jwtService.extractEmail(token);

        // ========== Étape 6 : Vérifier si l’utilisateur n’est pas déjà authentifié ==========
        // SecurityContextHolder.getContext() : récupère le contexte de sécurité de la requête.
        // getAuthentication() : retourne l’objet Authentication (qui contient user + rôles).
        //
        // Si c’est null -> Spring ne connaît aucun user authentifié pour cette requête.
        // Si ce n’est pas null -> un autre filtre a déjà fait l’auth, on évite de refaire.
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // ========== Étape 7 : Charger le user depuis la DB ==========
            // Pourquoi charger depuis DB ?
            // - récupérer les rôles/authorities réels et actuels
            // - vérifier que le user existe encore
            // - éviter de dépendre uniquement du contenu du token
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // ========== Étape 8 : Construire un objet Authentication ==========
            // UsernamePasswordAuthenticationToken est un type d'Authentication.
            // Ici on l'utilise pour dire à Spring :
            // - principal = userDetails (l’utilisateur)
            // - credentials = null (on ne garde pas le mot de passe)
            // - authorities = rôles (ROLE_CLIENT, ROLE_ADMIN, etc.)
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // ========== Étape 9 : Ajouter des infos techniques (optionnel) ==========
            // Ex: IP, infos de la requête. Pas obligatoire pour que JWT marche.
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ========== Étape 10 : Mettre l’Authentication dans le SecurityContext ==========
            // C’est LA ligne clé :
            // Après ça, Spring considère la requête comme authentifiée.
            // Donc :
            // - @AuthenticationPrincipal marche
            // - hasRole(...) marche
            // - les endpoints protégés deviennent accessibles avec le token
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // ========== Étape 11 : Continuer la chaîne ==========
        // Très important : sinon la requête n’arrive jamais au controller.
        chain.doFilter(request, response);
    }
}
