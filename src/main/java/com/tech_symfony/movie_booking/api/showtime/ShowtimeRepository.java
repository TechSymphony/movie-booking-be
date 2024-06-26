package com.tech_symfony.movie_booking.api.showtime;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ShowtimeRepository extends BaseAuthenticatedRepository<Showtime, Integer> {

	@PreAuthorize("hasAuthority( 'SAVE_SHOWTIME')")
	@Override
	Showtime save(Showtime showtime);

	@PreAuthorize("hasAuthority( 'DELETE_SHOWTIME')")
	@Override
	void deleteById(Integer id);
}
