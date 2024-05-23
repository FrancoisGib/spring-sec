package app.users;

import app.users.exception.UserAlreadyExistsException;
import app.users.exception.UserNotFoundException;
import app.users.models.User;
import app.users.models.UserCreationForm;
import app.users.models.UserUpdateForm;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	List<User> getAllUsers();

	User getUserById(Long id) throws UserNotFoundException;

	User createUser(UserCreationForm form) throws UserAlreadyExistsException;

	User updateUser(Long id, UserUpdateForm form) throws UserNotFoundException;

	void deleteUser(Long id) throws UserNotFoundException;

	User getUserByUsername(String username) throws UserNotFoundException;
}
