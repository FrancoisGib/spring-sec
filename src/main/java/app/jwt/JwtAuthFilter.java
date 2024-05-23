package app.jwt;

import app.users.details.CustomUserDetails;
import app.users.details.UserDetailsServiceImpl;
import app.users.details.UserPrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("accessToken")) {
					token = cookie.getValue();
				} else if (cookie.getName().equals("refreshToken")) {
					token = cookie.getValue();
					String username = jwtService.extractUsername(token);
					if (username != null) {
						jwtService.generateToken(username);
					} else {
						token = null;
					}
				}
			}
		}
		if (token != null) {
			String username = jwtService.extractUsername(token);
			if (username != null) {
				CustomUserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
				if (jwtService.validateToken(token, userDetails)) {
					UserPrincipalDetails userPrincipleDetails = new UserPrincipalDetails(userDetails.getId(), userDetails.getUsername());

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userPrincipleDetails, null,
						userDetails.getAuthorities());

					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}