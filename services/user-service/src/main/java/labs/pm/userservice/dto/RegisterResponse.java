package labs.pm.userservice.dto;

import labs.pm.userservice.entities.Role;

public record RegisterResponse(
      String message,
      Long userId,
      Role role
) {
}
