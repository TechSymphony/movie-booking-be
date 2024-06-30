package com.tech_symfony.movie_booking.api.seat_type;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(path = "seat-types")
public interface SeatTypeRepository extends BaseAuthenticatedRepository<SeatType, Integer> {

	@PreAuthorize("hasAuthority( 'SAVE_SEAT_TYPE')")
	@Override
	SeatType save(SeatType seatType);

	@PreAuthorize("hasAuthority( 'DELETE_SEAT_TYPE')")
	@Override
	void deleteById(Integer id);
}
