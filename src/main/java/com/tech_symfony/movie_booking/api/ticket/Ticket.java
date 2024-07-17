package com.tech_symfony.movie_booking.api.ticket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@Setter
@Getter

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Ticket extends BaseUUIDEntity {


	@ManyToOne
	@JoinColumn(
		name = "showtime_id",
		nullable = false
	)
	@NotNull(message = "Showtime  MUST not be null")
	private Showtime showtime;

	@ManyToOne
	@JoinColumn(
		name = "seat_id",
		nullable = false
	)
	@NotNull(message = "seat  MUST not be null")
	private Seat seat;


}
