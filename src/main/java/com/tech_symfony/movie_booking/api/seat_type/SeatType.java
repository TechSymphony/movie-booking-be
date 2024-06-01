package com.tech_symfony.movie_booking.api.seat_type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Set;

@Setter
@Getter

@Entity
@Table(name = "seat_types")
public class SeatType extends NamedEntity {

	@OneToMany(
		mappedBy = "type",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Seat> seats;

	@Column
	@Positive(message = "price must be positive")
	private Double price;

}
