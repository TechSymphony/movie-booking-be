package com.tech_symfony.movie_booking.api.ticket;

import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {


	Set<Ticket> findByShowtimeAndSeatIn(
		Showtime showtime,
		List<Seat> seatList
	);

}
