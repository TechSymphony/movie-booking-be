package com.tech_symfony.movie_booking.api.seat;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface SeatRepository extends BaseAuthenticatedRepository<Seat, Integer> {
	@Override
	List<Seat> findAll();

	@Override
	Optional<Seat> findById(Integer id);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'SAVE_SEAT')")
	@Override
	Seat save(Seat seat);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'DELETE_SEAT')")
	@Override
	void deleteById(Integer id);

}
