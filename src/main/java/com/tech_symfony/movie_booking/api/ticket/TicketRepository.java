package com.tech_symfony.movie_booking.api.ticket;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

	@Query("SELECT t FROM Ticket t " +
		"JOIN t.showtime s " +
		"JOIN t.seat sr " +
		"WHERE s.id = :showtimeId AND sr.id = :seatId")
	Set<Ticket> findByShowtimeAndSeat(
		@Param("showtimeId") Integer showtimeId,
		@Param("seatId") Integer seatId
	);

	@PreAuthorize("hasAuthority( 'SAVE_TICKET')")
	@Override
	Ticket save(Ticket ticket);

	@PreAuthorize("hasAuthority( 'DELETE_TICKET')")
	@Override
	void deleteById(UUID id);
}
