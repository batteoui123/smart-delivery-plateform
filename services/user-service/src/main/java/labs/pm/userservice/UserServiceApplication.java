package labs.pm.userservice;


import labs.pm.userservice.repo.UserRepositoty;
import labs.pm.userservice.security.CustomUserDetailsService;
import labs.pm.userservice.security.JwtProperties;
import labs.pm.userservice.security.JwtService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(JwtProperties.class)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner run(UserRepositoty userRepositoty, CustomUserDetailsService customerUserDetailsService, JwtService jwtService) {
        return args -> {
//            UserDetails userPrincipal=customerUserDetailsService.loadUserByUsername("john.smith@gmail.com");
//            System.out.println(userPrincipal.getUsername());
//            System.out.println(userPrincipal.getPassword());
//            String token="eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhaG1lZEBnbWFpbC5jb20iLCJ1aWQiOjIsInJvbGUiOiJDTElFTlQiLCJpYXQiOjE3NzAxMjEyMjAsImV4cCI6MTc3MDEyODQyMH0.9bzqjAOuGPYl_XBrAa2faL3ZqEWpZJ7Xokp33XHdF97YIKHBNQqzr4sKPXFxvKjH";
//            System.out.println("token is valide ?: "+ jwtService.isValid(token));



        };
    }

}
