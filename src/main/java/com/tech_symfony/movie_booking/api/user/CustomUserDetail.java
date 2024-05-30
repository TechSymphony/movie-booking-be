package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
@Builder
public class CustomUserDetail implements UserDetails {


	private String principle;

	private String credential;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays
			.stream(
				new String[]{"Geeks", "for", "Geeks"}
//				userInfoEntity
//				.getRoles()
//				.split(",")

			)
			.map(SimpleGrantedAuthority::new)
			.toList();
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
