package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public interface MovieService {
	Movie create(MovieDTO dataRaw);

	Movie update(Integer movieId, MovieDTO dataRaw);
}

@Service
@RequiredArgsConstructor
class DefaultMovieService implements MovieService {

	private final MovieRepository movieRepository;
	private final ShowtimeRepository showtimeRepository;
	private final MovieGenreRepository movieGenreRepository;

	private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

	private Set<Showtime> checkShowtimes(Set<Integer> showtimes) {

		if (showtimes == null) {
			throw new IllegalArgumentException("Showtimes field not found");
		}
		if (showtimes.isEmpty()) {
			throw new ForbidenMethodControllerException("Showtimes data not found");
		}

		Set<Showtime> showtimeSet = new HashSet<>();

		showtimes.forEach(showtime -> {
			if (!showtimeRepository.existsById(showtime)) {
				throw new ResourceNotFoundException("Showtime not found");
			}
			showtimeSet.add(showtimeRepository.findById(showtime).orElseThrow());
		});

		return showtimeSet;
	}

	private Set<MovieGenre> checkGernes(Set<Integer> genres) {
		if (genres == null) {
			throw new IllegalArgumentException("Genres field not found");
		}

		if (genres.isEmpty()) {
			throw new ForbidenMethodControllerException("Genres data not found");
		}

		Set<MovieGenre> movieGenreSet = new HashSet<>();
		genres.forEach(genre -> {
			if (!movieGenreRepository.existsById(genre)) {
				throw new ResourceNotFoundException("Movie genre not found");
			}
			movieGenreSet.add(movieGenreRepository.findById(genre).orElseThrow());
		});

		return movieGenreSet;
	}

	@Override
	public Movie create(MovieDTO dataRaw) {

		Movie movie = mapper.movieDtoToMovie(dataRaw);

		movie.setShowtimes(checkShowtimes(dataRaw.getShowtimes()));
		movie.setGenres(checkGernes(dataRaw.getGenres()));

		return movieRepository.save(movie);
	}

	@Override
	public Movie update(Integer movieId, MovieDTO dataRaw) {

		movieRepository.findById(movieId)
			.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

		// bring all fields from movieDto to movie then save it

		Movie movie = mapper.movieDtoToMovie(dataRaw);

		movie.setId(movieId);
		movie.setShowtimes(checkShowtimes(dataRaw.getShowtimes()));
		movie.setGenres(checkGernes(dataRaw.getGenres()));

		return movieRepository.save(movie);
	}
}
