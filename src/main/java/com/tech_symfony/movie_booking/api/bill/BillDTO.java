package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data

public class BillDTO extends BaseUUIDEntity {

//	@Digits(integer = 2, fraction = 0, message = "Changed point MUST be a number")
//	@Min(value = 0, message = "Changed point MUST MUST be at least 0")
//	@Max(value = 50, message = "Changed point MUST be less than or equal to 18")
//	private int changedPoint;

	@NotNull(message = "Showtime id MUST not be null")
	@Min(1)
	private int showtimeId;

	@NotEmpty(message = "seat MUST not be empty")
	@NotNull(message = "seat MUST not be null")
	private List<Integer> seatId;


}
