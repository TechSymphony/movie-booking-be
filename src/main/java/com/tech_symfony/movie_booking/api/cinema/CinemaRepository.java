package com.tech_symfony.movie_booking.api.cinema;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CinemaRepository extends BaseAuthenticatedRepository<Cinema, Integer> {


	@PreAuthorize("hasAuthority( 'SAVE_CINEMA')")
	@Override
	Cinema save(Cinema cinema);

	@PreAuthorize("hasAuthority( 'DELETE_CINEMA')")
	@Override
	void deleteById(Integer id);
}
