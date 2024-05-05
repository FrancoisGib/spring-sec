package app.user.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationForm {
    @NotBlank
    @NotNull
    private String username;

    @NotEmpty
    @NotBlank
    @Email
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Please provide a right email")
    private String email;

    @NotEmpty
    @NotBlank
    private String password;
}
