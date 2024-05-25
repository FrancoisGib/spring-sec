package app.users.roles;

import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {
	private final RoleService roleService;

	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody RoleCreationForm form) {
		return new ResponseEntity<>(roleService.createRole(new Role(form.getId(), form.getName(), new HashSet<>())), HttpStatus.CREATED);
	}
}
