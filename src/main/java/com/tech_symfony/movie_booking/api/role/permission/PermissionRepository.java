package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface PermissionRepository  extends BaseAuthenticatedRepository<Permission, Integer> {
	@Override
	@PreAuthorize("@permissionService.hasPermission(authentication, 'READ_PERMISSION')")
	List<Permission> findAll();

	@Override
	default Optional<Permission> findById(Integer id){
		return null;
	}

	@Override
	default Permission save(Permission permission){
		return null;
	}

	@Override
	default void deleteById(Integer id){}
}
