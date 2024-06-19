package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.system.exception.RoleInUseException;
import com.tech_symfony.movie_booking.system.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Role update(Integer id, Role role) {
		return roleRepository.findById(id)
			.map(existingRole -> {
				existingRole.setName(role.getName());
				existingRole.setPermissions(role.getPermissions());
				return roleRepository.save(existingRole);
			})
			.orElseThrow(() -> new RoleNotFoundException(id));
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
