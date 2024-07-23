package com.tech_symfony.movie_booking.api.cinema;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "cinemas")
public class Cinema extends NamedEntity {

	@NotBlank(message = "Cinema address must not be blank")
	@NotNull(message = "Cinema address must not be null")
	private String address;

	@NotBlank(message = "Cinema district must not be blank")
	@NotNull(message = "Cinema district must not be null")
	private String district;

	@NotBlank(message = "Cinema city must not be blank")
	@NotNull(message = "Cinema city must not be null")
	private String city;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "Cinema description must not be blank")
	@NotNull(message = "Cinema description must not be null")
	private String description;

	@Enumerated(EnumType.STRING)
	@NotBlank(message = "Cinema status must not be blank")
	private CinemaStatus status;

	@NotBlank(message = "Cinema phone number must not be blank")
	@NotNull(message = "Cinema phone number must not be null")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToMany(
		mappedBy = "cinema",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	@JsonManagedReference
	private Set<Room> rooms;
}
