package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;


@Getter
@Setter
public class MovieDTO extends NamedEntity {

	private String code;

	@NotBlank(message = "Sub name MUST not be blank")
	private String subName;

	@NotNull
	@NotBlank(message = "Director MUST not be blank")
	private String director;

	@NotNull
	@NotBlank(message = "Caster MUST not be blank")
	private String caster;

	@NotNull(message = "Release date MUST not be null")
	@Future(message = "Release date MUST be future")
	private Date releaseDate;

	@NotNull(message = "End date MUST not be null")
	@Future(message = "End date MUST be future")
	private Date endDate;

	@NotNull(message = "Running time MUST not be null")
	@Positive(message = "Running time MUST not be number")
	private Integer runningTime;

	@NotBlank(message = "Language MUST not be blank")
	private String language;

	@NotBlank(message = "Description MUST not be blank")
	private Integer numberOfRatings;

	@NotBlank(message = "SumOfRatings MUST not be blank")
	private Integer sumOfRatings;

	@NotBlank(message = "Description MUST not be blank")
	private String description;

	@NotBlank(message = "Poster MUST not be blank")
	private String poster;

	@NotNull
	@NotBlank(message = "Trailer MUST not be blank")
	private String trailer;

	@Positive(message = "Rated MUST not be negative")
	@NotNull(message = "Rated MUST not be null")
	@Max(value = 18, message = "Age for rated MUST be less than or equal to 18")
	private Integer rated;

	private String slug;

	@NotEmpty(message = "Showtimes MUST not be empty")
	private Set<Integer> showtimes;

	@NotEmpty(message = "Genres MUST not be empty")
	private Set<Integer> genres;
}
