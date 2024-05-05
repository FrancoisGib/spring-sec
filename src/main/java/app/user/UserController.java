package app.user;

import app.user.exception.UserAlreadyExistsException;
import app.user.exception.UserNotFoundException;
import app.user.models.UserCreationForm;
import app.user.models.UserDTO;
import app.user.models.UserUpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService; // pas besoin d'autowired car RequiredArgsConstructor

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.getUserById(id)), HttpStatus.FOUND);
    }

    @PutMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreationForm form) throws UserAlreadyExistsException {
        return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.createUser(form)), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateForm form) throws UserNotFoundException {
        return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.updateUser(id, form)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.getAllUsers()), HttpStatus.OK);
    }
}
