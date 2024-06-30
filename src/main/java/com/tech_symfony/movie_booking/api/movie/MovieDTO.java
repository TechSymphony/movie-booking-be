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

	private String subName;

	private String director;

	private String caster;

	private String releaseDate;

	private String endDate;

	private Integer runningTime;

	private String language;

	private Integer numberOfRatings;

	private Integer sumOfRatings;

	private String description;

	private String poster;

	private String trailer;

	private Integer rated;

	private String slug;

	private Set<Integer> showtimes;

	private Set<Integer> genres;
}
