package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

public interface MovieService {
	Movie create(MovieDTO dataRaw);

	Movie update(MovieDTO dataRaw);
}

@Service
@RequiredArgsConstructor
class DefaultMovieService implements MovieService {

	private final MovieRepository movieRepository;
	private final ShowtimeRepository showtimeRepository;
	private final MovieGenreRepository movieGenreRepository;

	private void checkShowtimes(Set<Integer> showtimes) {
		if (showtimes.isEmpty()) {
			throw new ForbidenMethodControllerException("Data not found");
		}

		showtimes.forEach(showtime -> {
			if (!showtimeRepository.existsById(showtime)) {
				throw new ResourceNotFoundException("Showtime not found");
			}
		});
	}

	private void checkGernes(Set<Integer> genres) {
		if (genres.isEmpty()) {
			throw new ForbidenMethodControllerException("Data not found");
		}

		genres.forEach(genre -> {
			if (!movieGenreRepository.existsById(genre)) {
				throw new ResourceNotFoundException("Movie genre not found");
			}
		});
	}

	@Override
	public Movie create(MovieDTO dataRaw) {

		checkShowtimes(dataRaw.getShowtimes());
		checkGernes(dataRaw.getGenres());

		Movie movie = new Movie();

		movie.setCode(dataRaw.getCode());
		movie.setSubName(dataRaw.getSubName());
		movie.setDirector(dataRaw.getDirector());
		movie.setCaster(dataRaw.getCaster());
		movie.setReleaseDate(dataRaw.getReleaseDate());
		movie.setEndDate(dataRaw.getEndDate());
		movie.setRunningTime(dataRaw.getRunningTime());
		movie.setLanguage(dataRaw.getLanguage());
		movie.setNumberOfRatings(dataRaw.getNumberOfRatings());
		movie.setSumOfRatings(dataRaw.getSumOfRatings());
		movie.setDescription(dataRaw.getDescription());
		movie.setPoster(dataRaw.getPoster());
		movie.setTrailer(dataRaw.getTrailer());
		movie.setRated(dataRaw.getRated());

		Set<Showtime> showtimes = new HashSet<>(showtimeRepository.findAllById(dataRaw.getShowtimes()));

		Set<MovieGenre> movieGenres = new HashSet<>(movieGenreRepository.findAllById(dataRaw.getGenres()));

		movie.setShowtimes(showtimes);
		movie.setGenres(movieGenres);

		movieRepository.save(movie);

		return null;
	}

	@Override
	public Movie update(MovieDTO dataRaw) {
		return null;
	}
}
