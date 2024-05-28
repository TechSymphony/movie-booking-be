package com.tech_symfony.movie_booking.api.showtime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "showtimes")

public class Showtime extends BaseEntity {

	@Future(message = "The start date must be in the future")
	@NotEmpty(message = "Start date must not be null")
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;


	@Temporal(TemporalType.TIME)
	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "running_time")
	private Integer runningTime;

	@Column(name = "status", columnDefinition = "boolean default true")
	private Boolean status;

	@NotEmpty(message = "room id must not be empty")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "movie_id",
		nullable = false
	)
	private Movie movie;


	@NotEmpty(message = "room id must not be empty")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "room_id",
		nullable = false
	)
	private Room room;

}
