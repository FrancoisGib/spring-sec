package app.users.roles;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoleCreationForm {
	private String name;
	private Long id;
}
