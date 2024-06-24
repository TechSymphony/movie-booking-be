package com.tech_symfony.movie_booking.api.seat_type;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface SeatTypeRepository extends BaseAuthenticatedRepository<SeatType, Integer> {
	@Override
	List<SeatType> findAll();

	@Override
	Optional<SeatType> findById(Integer id);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'SAVE_SEAT_TYPE')")
	@Override
	SeatType save(SeatType seatType);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'DELETE_SEAT_TYPE')")
	@Override
	void deleteById(Integer id);
}
