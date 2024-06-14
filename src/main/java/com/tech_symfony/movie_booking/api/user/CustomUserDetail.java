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
//		String role = this.role.getName();

//		String[] authorities = new String[]{"ROLE_USER"};
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		for (AppRole role : user.getRoles()) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(this.role != null)
			grantedAuthorities.add(new SimpleGrantedAuthority(this.role.getName()));
		
		return grantedAuthorities;
//		return Arrays
//			.stream(
//				authorities
////				userInfoEntity
////				.getRoles()
////				.split(",")
//
//			)
//			.map(SimpleGrantedAuthority::new)
//			.toList();
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
