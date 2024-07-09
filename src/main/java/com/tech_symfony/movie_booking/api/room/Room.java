package com.tech_symfony.movie_booking.api.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;


import java.util.Set;

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

//	private String slug;

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
	@RestResource(exported = false)
	private Cinema cinema;

	@OneToMany(
		mappedBy = "room",
		fetch = FetchType.LAZY,
		cascade = CascadeType.PERSIST
	)
	private Set<Seat> seats;


}
