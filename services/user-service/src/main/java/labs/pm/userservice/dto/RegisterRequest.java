package labs.pm.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import labs.pm.userservice.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Email @NotBlank
    private String email;
    @NotBlank @Size(min = 6,max = 30)
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
