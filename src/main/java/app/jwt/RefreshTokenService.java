package app.jwt;

import app.jwt.models.RefreshToken;
import app.user.UserRepository;
import app.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${jwt.refreshTokenExpiry}")
    private Long jwtRefreshExpiry;

    public RefreshToken createRefreshToken(String username) throws UserNotFoundException {
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username)))
                .token(UUID.randomUUID().toString())
                .expiryDate(new Date(System.currentTimeMillis() + jwtRefreshExpiry)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(new Date(System.currentTimeMillis())) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + "Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}