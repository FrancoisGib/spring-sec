package app.auth;

import app.users.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse {
  private Long id;
  private String username;
  private Role role;
}