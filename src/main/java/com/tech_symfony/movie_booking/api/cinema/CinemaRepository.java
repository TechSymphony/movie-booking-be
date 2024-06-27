package com.tech_symfony.movie_booking.api.cinema;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CinemaRepository extends BaseAuthenticatedRepository<Cinema, Integer> {

	@PreAuthorize("@permissionService.hasPermission(authentication, 'SAVE_CINEMA')")
	@Override
	Cinema save(Cinema cinema);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'DELETE_CINEMA')")
	@Override
	void deleteById(Integer id);
}
