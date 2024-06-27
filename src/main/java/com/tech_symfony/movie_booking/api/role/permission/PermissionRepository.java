package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = PermissionProjector.class)
@PreAuthorize("hasAuthority('READ_PERMISSION')")
public interface PermissionRepository extends BaseAuthenticatedRepository<Permission, Integer> {
	Optional<Permission> findByIdIn(List<Integer> ids);

	@RestResource(exported = false)
	@Override
	Optional<Permission> findById(Integer id);

	@RestResource(exported = false)
	@Override
	Permission save(Permission permission);

	@RestResource(exported = false)
	@Override
	void deleteById(Integer id);
}
