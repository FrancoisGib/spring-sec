package app.jwt;

import app.jwt.models.RefreshToken;
import app.users.UserRepository;
import app.users.exception.UserNotFoundException;
import app.users.models.User;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	private final UserRepository userRepository;

	@Value("${jwt.refreshTokenExpiry}")
	private Long jwtRefreshExpiry;

	public RefreshToken createRefreshToken(String username) throws UserNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
		RefreshToken token = refreshTokenRepository.findByUserInfo(user);
		if (token != null) {
			token = verifyExpiration(token);
			if (token != null) {
				return token;
			}
		}
		RefreshToken refreshToken = RefreshToken.builder()
			.userInfo(userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username)))
			.token(UUID.randomUUID().toString())
			.expiryDate(new Date(System.currentTimeMillis() + jwtRefreshExpiry))
			.build();
		return refreshTokenRepository.save(refreshToken);
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(new Date(System.currentTimeMillis())) < 0) {
			refreshTokenRepository.delete(token);
			throw new RuntimeException(
				token.getToken() + "Refresh token is expired. Please make a new login..!");
		}
		return token;
	}
}