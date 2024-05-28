package com.tech_symfony.movie_booking.api.room;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;


import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "rooms")
public class Room extends NamedEntity {

	@Positive(message = "room total must be positive")
	@Column(name = "total_seats")
	private Integer totalSeats;

	@Column(name = "available_seats")
	private Integer availableSeats;

	private String slug;

	@OneToMany(
		mappedBy = "room",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Showtime> showtimes;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "cinema_id",
		nullable = false
	)
	private Cinema cinema;

	@OneToMany(
		mappedBy = "room",
		fetch = FetchType.LAZY,
		cascade = CascadeType.PERSIST
	)
	private Set<Seat> seats;


}
