package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface MovieMapper {

	@Mapping(target = "showtimes", source = "showtimes", ignore = true)
	@Mapping(target = "genres", source = "genres", ignore = true)
	MovieDTO movieToMovieDto(Movie movie);

	@Mapping(target = "showtimes", source = "showtimes", ignore = true)
	@Mapping(target = "genres", source = "genres", ignore = true)
	Movie movieDtoToMovie(MovieDTO movieDto);

	default Date dateInStringToSqlDate(String date) {
		return Date.valueOf(date);
	}
}
