package app.user;

import app.user.exception.UserAlreadyExistsException;
import app.user.exception.UserNotFoundException;
import app.user.models.User;
import app.user.models.UserCreationForm;
import app.user.models.UserUpdateForm;
import app.user.models.UsersCountByDate;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository; // final car EnableJpaRepositories

  private final PasswordEncoder passwordEncoder;

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

  public List<UsersCountByDate> getUsersBetweenDatesAndAllUsers(Date start, Date end) {
	return userRepository.getAllUsersCountAndCountByDate(start, end);
  }
}
