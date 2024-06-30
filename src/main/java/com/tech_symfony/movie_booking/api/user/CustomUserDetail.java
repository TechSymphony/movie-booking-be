package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
public class CustomUserDetail implements UserDetails {


	private String principle;

	private String credential;

	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

    
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		role.getPermissions().forEach(permission ->
			grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()))
		);

		return grantedAuthorities;

	}

	@Override
	public String getPassword() {
		return this.credential;
	}

	@Override
	public String getUsername() {
		return this.principle;
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
