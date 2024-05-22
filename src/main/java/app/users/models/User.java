package app.users.models;

import app.users.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
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
  private static final int USERNAME_LENGTH = 20;

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

  @Temporal(TemporalType.DATE)
  @Column(name = "account_creation_date", nullable = false)
  private Date accountCreationDate;

  @ManyToOne
  private Role role;
}
