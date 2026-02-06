package labs.pm.userservice.security;

import labs.pm.userservice.entities.User;
import labs.pm.userservice.repo.UserRepositoty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoty userRepo;

    public CustomUserDetailsService(UserRepositoty userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));
        return UserPrincipal.fromUser(user);
    }
}
