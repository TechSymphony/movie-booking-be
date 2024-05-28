package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Setter
@Getter
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
	@JsonIgnore
	public String getPassword() {
		return this.credential;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return this.principle;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}


}
