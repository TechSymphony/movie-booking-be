package com.tech_symfony.movie_booking.api.ticket;

import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "ticket", types = {Ticket.class})
interface TicketProjector {

	UUID getId();

	@Transactional
	Bill getBill();

	Seat getSeat();


}
