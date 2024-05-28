package com.tech_symfony.movie_booking.api.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.BaseEntity;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;


@Data
@Entity
@Table(name = "tickets")

public class Ticket extends BaseUUIDEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "bill_id",
		nullable = false
	)
	private Bill bill;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "showtime_id",
		nullable = false
	)
	@NotBlank(message = "Showtime id MUST not be blank")
	private Showtime showtime;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "seat_id",
		nullable = false
	)
	private Seat seat;


}
