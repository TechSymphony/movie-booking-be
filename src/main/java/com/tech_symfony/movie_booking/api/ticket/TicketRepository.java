package com.tech_symfony.movie_booking.api.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;
import java.util.UUID;

//@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

	@RestResource(exported = false)
	@Query("SELECT t FROM Ticket t " +
		"JOIN t.showtime s " +
		"JOIN t.seat sr " +
		"WHERE s.id = :showtimeId AND sr.id = :seatId")
	Set<Ticket> findByShowtimeAndSeat(
		@Param("showtimeId") Integer showtimeId,
		@Param("seatId") Integer seatId
	);
}
