package app.users.roles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {
	private final RoleRepository roleRepository;

	public Role getRoleByName(String roleName) {
		return roleRepository.findByName(roleName);
	}
}
