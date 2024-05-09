package app.jwt;

import app.user.details.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	    String token = null;
	    if (request.getCookies() != null) {
		    for (Cookie cookie : request.getCookies()) {
			    if (cookie.getName().equals("accessToken")) {
				    token = cookie.getValue();
			    }
		    }
	    }
	    String username = jwtService.extractUsername(token);
	    if (token != null && username != null) {
		    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
		    if (jwtService.validateToken(token, userDetails)) {
			    UsernamePasswordAuthenticationToken authenticationToken =
				    new UsernamePasswordAuthenticationToken(userDetails, null,
					    userDetails.getAuthorities());
			    authenticationToken.setDetails(
				    new WebAuthenticationDetailsSource().buildDetails(request));
			    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		    }
	    }
	    filterChain.doFilter(request, response);
    }
}