package app.auth;

import app.user.models.UserRole;
import java.util.Set;
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
  private Set<UserRole> roles;
}