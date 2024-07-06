package com.tech_symfony.movie_booking.api.showtime;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(excerptProjection = ShowtimeProjection.class)
public interface ShowtimeRepository extends BaseAuthenticatedRepository<Showtime, Integer> {

	@PreAuthorize("hasAuthority( 'SAVE_SHOWTIME')")
	@Override
	Showtime save(Showtime showtime);

	@PreAuthorize("hasAuthority( 'DELETE_SHOWTIME')")
	@Override
	void deleteById(Integer id);
}
