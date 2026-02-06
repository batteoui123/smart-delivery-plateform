package labs.pm.userservice.controllers;

import labs.pm.userservice.entities.User;
import labs.pm.userservice.repo.UserRepositoty;
import labs.pm.userservice.security.UserPrincipal;
import labs.pm.userservice.services.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepositoty userRepositoty;
    private final AuthService authService;

    public UserController(UserRepositoty userRepositoty, AuthService authService) {
        this.userRepositoty = userRepositoty;
        this.authService = authService;
    }

    @GetMapping("/me")
    public User me(@AuthenticationPrincipal UserPrincipal user) {
        System.out.println("authenticated user: ");
        System.out.println(user.getId());
        return userRepositoty.findById(user.getId()).orElseThrow(()->new RuntimeException("User not found"));
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return authService.getUserById(id);
    }
    @GetMapping("/drivers/available")
    public List<User> findAvailableDrivers() {
        return authService.getAvailableDrivers();
    }
}
