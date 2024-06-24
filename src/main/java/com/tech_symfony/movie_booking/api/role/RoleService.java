package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.role.exception.RoleInUseException;
import com.tech_symfony.movie_booking.api.role.exception.RoleNotFoundException;
import com.tech_symfony.movie_booking.api.role.permission.Permission;
import com.tech_symfony.movie_booking.api.role.permission.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface RoleService {
	List<Role> findAll();
	Role findById(Integer id);
	Role save(Role role);
	Role update(Integer id, Role role);
	Boolean delete(Integer id);
}

@Service
@RequiredArgsConstructor
class DefaultRoleService implements RoleService{

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(Integer id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
		return role;
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public Role update(Integer id, Role role) {
		Role oldRole = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
		System.out.println(id);
		Set<Permission> existingPermissions = role.getPermissions().stream()
			.map(permission -> {
				System.out.println(permission.getId());
				return permissionRepository.findById(permission.getId())
					.orElseThrow(() -> new RuntimeException("Permission not found: " + permission.getId()));
			})
			.collect(Collectors.toSet());
		oldRole.setPermissions(existingPermissions );
		return oldRole;
	}

	@Override
	public Boolean delete(Integer id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
		if (roleRepository.existsByUsersRole(id)) {
			throw new RoleInUseException(id);
		}
		roleRepository.delete(role);
		return !roleRepository.existsById(id);
	}
}
