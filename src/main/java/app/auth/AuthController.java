package app.auth;

import app.jwt.JwtService;
import app.jwt.RefreshTokenService;
import app.jwt.models.JwtResponseDTO;
import app.jwt.models.RefreshToken;
import app.jwt.models.RefreshTokenRequestDTO;
import app.user.UserMapper;
import app.user.UserService;
import app.user.exception.UserAlreadyExistsException;
import app.user.exception.UserNotFoundException;
import app.user.models.UserCreationForm;
import app.user.models.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AuthController {
	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final RefreshTokenService refreshTokenService;

	private final UserService userService;

	@Value("${jwt.cookieExpiry}")
	private Long cookieExpiry;

	@PostMapping("/login")
	public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO,
			HttpServletResponse response)
			throws UserNotFoundException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),
						authRequestDTO.getPassword()));
		if (authentication.isAuthenticated()) {
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
			String accessToken = jwtService.generateToken(authRequestDTO.getUsername());
			ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(cookieExpiry)
					.build();
			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			return JwtResponseDTO.builder()
					.accessToken(accessToken)
					.token(refreshToken.getToken()).build();
		} else {
			throw new UsernameNotFoundException("invalid user request..!!");
		}
	}

	@PutMapping("/register")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreationForm form)
			throws UserAlreadyExistsException {
		return new ResponseEntity<>(UserMapper.INSTANCE.toDto(userService.createUser(form)),
				HttpStatus.CREATED);
	}

	@PostMapping("/refresh")
	public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
		return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUserInfo)
				.map(userInfo -> {
					String accessToken = jwtService.generateToken(userInfo.getUsername());
					return JwtResponseDTO.builder()
							.accessToken(accessToken)
							.token(refreshTokenRequestDTO.getToken()).build();
				}).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
	}
}
