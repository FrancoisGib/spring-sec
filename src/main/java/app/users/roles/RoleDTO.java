package app.users.roles;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleDTO {
	private String name;
	private Set<PermissionDTO> permissions;
}
