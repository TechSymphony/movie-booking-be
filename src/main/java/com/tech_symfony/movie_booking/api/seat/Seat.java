package com.tech_symfony.movie_booking.api.seat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.api.seat_type.SeatType;
import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;


import java.util.Set;


@Setter
@Getter

@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {

	@NotNull
	@AssertTrue
	@Column(name = "status", columnDefinition = "boolean default true")
	private Boolean status;

	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "room_id",
		nullable = false
	)
	@RestResource(exported = false)
	private Room room;


	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "seat_type_id",
		referencedColumnName = "id",
		nullable = false
	)
	@JsonBackReference
	@RestResource(exported = false)

	private SeatType type;

	@OneToMany(
		mappedBy = "seat",
		fetch = FetchType.LAZY,
		cascade = CascadeType.PERSIST
	)
	@JsonBackReference

	private Set<Ticket> tickets;


	@Column(name = "row_name")
	@NotBlank(message = "seat room row must not be blank")
	@NotNull(message = "seat room row must not be null")
	private String rowName;

	@Column(name = "row_index")
	@Positive
	private Integer rowIndex;

	@Transient
	private Boolean isReserved;
}
