package com.tech_symfony.movie_booking.api.ticket;

import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

	@RestResource(exported = false)
	Set<Ticket> findByShowtimeAndSeatIn(
		Showtime showtime,
		List<Seat> seatList
	);

}
