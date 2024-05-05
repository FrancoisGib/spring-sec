package app.auth;

import app.user.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthRequest {
    private Long id;
    private String username;
    private String password;
    private Set<UserRole> roles;
}