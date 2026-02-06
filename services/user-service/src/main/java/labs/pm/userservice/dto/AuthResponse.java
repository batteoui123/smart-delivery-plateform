package labs.pm.userservice.dto;

import labs.pm.userservice.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String type;
    private String accessToken;
    private Long userId;
    private Role role;
}
