package labs.pm.userservice.controllers;

import labs.pm.userservice.dto.AuthResponse;
import labs.pm.userservice.dto.LoginRequest;
import labs.pm.userservice.dto.RegisterRequest;
import labs.pm.userservice.dto.RegisterResponse;
import labs.pm.userservice.entities.User;
import labs.pm.userservice.repo.UserRepositoty;
import labs.pm.userservice.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepositoty userRepositoty;

    public AuthController(final AuthService authService, UserRepositoty userRepositoty) {
        this.authService = authService;
        this.userRepositoty = userRepositoty;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepositoty.findAll();
    }
}
