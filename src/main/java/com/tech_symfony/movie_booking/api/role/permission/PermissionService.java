package com.tech_symfony.movie_booking.api.role.permission;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

	public boolean hasPermission(Authentication authentication, String permission) {
		return authentication.getAuthorities().stream()
			.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permission));
	}
}
