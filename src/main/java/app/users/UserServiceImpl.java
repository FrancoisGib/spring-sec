package app.users;

import app.users.exception.UserAlreadyExistsException;
import app.users.exception.UserNotFoundException;
import app.users.models.User;
import app.users.models.UserCreationForm;
import app.users.models.UserUpdateForm;
import app.users.roles.RoleService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) throws UserNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public User getUserByUsername(String username) throws UserNotFoundException {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new UserNotFoundException(username));
	}

	public User createUser(UserCreationForm form) throws UserAlreadyExistsException {
		try {
			return userRepository.save(
				User.builder()
					.username(form.getUsername())
					.email(form.getEmail())
					.password(passwordEncoder.encode(form.getPassword()))
					.accountCreationDate(new Date(System.currentTimeMillis()))
					.role(roleService.getRoleByName("USER"))
					.build());
		} catch (Exception e) {
			throw new UserAlreadyExistsException(form.getUsername());
		}
	}

	public User updateUser(Long id, UserUpdateForm form) throws UserNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		if (form.getUsername() != null) {
			user.setUsername(form.getUsername());
		}
		if (form.getPassword() != null) {
			user.setPassword(form.getPassword());
		}
		if (form.getEmail() != null) {
			user.setEmail(form.getEmail());
		}
		return userRepository.save(user);
	}

	public void deleteUser(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userRepository.delete(user);
	}
}
