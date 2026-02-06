package labs.pm.userservice.services;


import jakarta.transaction.Transactional;
import labs.pm.userservice.dto.AuthResponse;
import labs.pm.userservice.dto.LoginRequest;
import labs.pm.userservice.dto.RegisterRequest;
import labs.pm.userservice.dto.RegisterResponse;
import labs.pm.userservice.entities.DriverAvailability;
import labs.pm.userservice.entities.Role;
import labs.pm.userservice.entities.User;
import labs.pm.userservice.repo.UserRepositoty;
import labs.pm.userservice.security.JwtService;
import labs.pm.userservice.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthService {

    private final UserRepositoty userRepositoty;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepositoty userRepositoty, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepositoty = userRepositoty;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        String email=registerRequest.getEmail().toLowerCase().trim();
        if(userRepositoty.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email is already exist !");
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhone(registerRequest.getPhone());
        user.setRole(registerRequest.getRole());
        if(registerRequest.getRole()== Role.DRIVER) {
            user.setDriverAvailability(DriverAvailability.AVAILABLE);
        }else
            user.setDriverAvailability(DriverAvailability.UNAVAILABLE);
        User savedUser=userRepositoty.save(user);
        return new RegisterResponse("inscription reussie", savedUser.getId(), savedUser.getRole());
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user=userRepositoty.findByEmail(loginRequest.getEmail().toLowerCase().trim()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants invalides"));
        String token =jwtService.generateToken(UserPrincipal.fromUser(user));
        return new AuthResponse("Bearer",token, user.getId(), user.getRole());
    }

    public User getUserById(Long id){
        return  userRepositoty.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAvailableDrivers(){
         return userRepositoty.findAvailableDrivers(Role.DRIVER,DriverAvailability.AVAILABLE);
    }
}
