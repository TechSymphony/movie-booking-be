package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.api.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService {

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	public boolean hasPermission(Authentication authentication, String permission) {
		if (authentication.getPrincipal() instanceof Jwt) {
			Jwt jwt = (Jwt) authentication.getPrincipal();
			String email = jwt.getClaimAsString("sub");
			List<String> userPermissions = roleRepository.getPermissionByUser(email);
			return userPermissions.contains(permission);
		}
		return false;
	}

	public List<Permission> getAllPermission(){return permissionRepository.findAll();}
}
