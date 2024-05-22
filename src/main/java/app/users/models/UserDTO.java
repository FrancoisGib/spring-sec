package app.users.models;

import app.users.roles.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private String email;
  private RoleDTO role;
}
