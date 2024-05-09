package app.user;

import app.user.exception.UserNotFoundException;
import app.user.models.UserDTO;
import app.user.models.UserUpdateForm;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService; // pas besoin d'autowired car RequiredArgsConstructor

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
	return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.getUserById(id)),
		HttpStatus.FOUND);
  }

  @PostMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
											@Valid @RequestBody UserUpdateForm form)
	  throws UserNotFoundException {
	return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.updateUser(id, form)),
		HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
	userService.deleteUser(id);
	return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
	return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.getAllUsers()),
		HttpStatus.OK);
  }
}
