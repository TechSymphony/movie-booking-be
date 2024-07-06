package com.tech_symfony.movie_booking.api.bill;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BillRequestDTO {

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
