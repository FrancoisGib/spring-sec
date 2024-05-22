package app.users.details;

import app.users.models.User;
import app.users.roles.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
public class CustomUserDetails extends User implements UserDetails {
	private final Long id;
	private final String username;
	private final String password;
	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		List<GrantedAuthority> auths = new ArrayList<>();
		for (Permission permission: user.getRole().getPermissions()) {
			auths.add(new SimpleGrantedAuthority(permission.getName()));
		}
		this.authorities = auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}