package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter

@Entity
@Table(name = "movies")
public class Movie extends NamedEntity {


	private String code;


	@Column(name = "sub_name")
	@NotBlank(message = "Sub name MUST not be plank")
	private String subName;

	@NotNull
	@NotBlank(message = "Director MUST not be plank")
	private String director;

	@NotNull
	@NotBlank(message = "Caster MUST not be plank")
	private String caster;

	@NotNull(message = "Release date MUST not be null")
	@Future(message = "Release date MUST be future")
	@Column(name = "release_date")
	private Date releaseDate;

	@NotNull(message = "End date MUST not be null")
	@Future(message = "End date MUST be future")
	@Column(name = "end_date")
	private Date endDate;

	@NotNull(message = "Running time MUST not be null")
	@Positive(message = "Running time MUST not be number")
	@Column(name = "running_time")
	private Integer runningTime;

	@NotBlank(message = "Language MUST not be blank")
	private String language;


	@NotBlank(message = "Description MUST not be blank")
	@Column(name = "number_of_ratings")
	private Integer numberOfRatings;

	@Column(name = "sum_of_ratings")
	@NotBlank(message = "Trailer MUST not be blank")
	private Integer sumOfRatings;

	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "Producer MUST not be blank")
	private String description;

	@NotBlank(message = "Poster MUST not be blank")
	private String poster;

	@Column(name = "horizontal_poster")
	private String horizontalPoster;

	@NotNull
	@NotBlank(message = "Trailer MUST not be plank")
	private String trailer;

	@Positive(message = "Rated MUST not be negative")
	@NotNull(message = "Rated MUST not be null")
	@Max(value = 18, message = "Age for rated MUST be less than or equal to 18")
	private Integer rated;


	private String slug;

	@NotEmpty(message = "Showtime MUST not be empty")
	@OneToMany(
		mappedBy = "movie",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Showtime> showtimes;


	@NotEmpty(message = "Genre MUST not be empty")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
		name = "movie_genre",
		joinColumns = @JoinColumn(name = "movie_id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "movie_genre_id", nullable = false)
	)
	private Set<MovieGenre> genres;
	//
//	@OneToMany(
//		mappedBy = "movie",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<MovieImageEntity> images;
//


}
