package app.config;

import app.users.details.UserPrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthorizer {

	public boolean isAdmin() {
		System.out.println("admin");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			System.out.println(authority.getAuthority());
		}
		return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("admin:read"));
	}

	public boolean isUser() {
		System.out.println("user");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			System.out.println(authority.getAuthority());
		}
		return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("user:read"));
	}

	public boolean canReadSelf(String permission, Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipalDetails principal = (UserPrincipalDetails) authentication.getPrincipal();
		return principal.getId().equals(id)
			&& authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(permission));
	}
}