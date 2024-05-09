package app.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_app")
public class User {
  private static final int USERNAME_LENGTH = 30;

  @ManyToMany(fetch = FetchType.EAGER)
  private final Set<UserRole> roles = new HashSet<>();
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Long id;
  @Column(name = "username", nullable = false, length = USERNAME_LENGTH, unique = true)
  private String username;
  @Column(name = "email", nullable = false, unique = true)
  @JsonIgnore
  private String email;
  @Column(name = "password", nullable = false)
  @JsonIgnore
  private String password;
}
