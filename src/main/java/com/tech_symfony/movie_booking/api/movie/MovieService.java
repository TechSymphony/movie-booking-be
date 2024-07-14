package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie.exception.MovieNotFoundException;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface MovieService {
	Movie create(MovieDTO dataRaw);

	Movie update(Integer movieId, MovieDTO dataRaw);

	Boolean delete(Integer movieId);

	Movie findById(Integer movieId);

	List<Movie> findAll();
}

@Service
@RequiredArgsConstructor
class DefaultMovieService implements MovieService {

	private final MovieRepository movieRepository;
	private final ShowtimeRepository showtimeRepository;
	private final MovieGenreRepository movieGenreRepository;

	private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

	private boolean checkGernes(Set<Integer> genres) {

		if (genres == null) {
			return false;
		}

		if (genres.isEmpty()) {
			throw new ForbidenMethodControllerException("Genres data not found");
		}

		genres.forEach(genre -> {
			MovieGenre _genre = movieGenreRepository.findById(genre).orElseThrow(() -> new ResourceNotFoundException("Not found genre with id: " + genre));

			movie.addGenre(_genre);
		});
		return true;
	}

	private Movie movie;

	@Override
	@Transactional
	public Movie create(MovieDTO dataRaw) {
		movie = movieRepository.save(mapper.movieDtoToMovie(dataRaw));
		checkGernes(dataRaw.getGenres());
		return movieRepository.save(movie);
	}

	@Override
	@Transactional
	public Movie update(Integer movieId, MovieDTO dataRaw) {

		movieRepository.findById(movieId)
			.orElseThrow(() -> new MovieNotFoundException(movieId));

		// bring all fields from movieDto to movie then save it

		movie = mapper.movieDtoToMovie(dataRaw);
		movie.setId(movieId);
		checkGernes(dataRaw.getGenres());

		return movieRepository.save(movie);
	}

	public Boolean delete(Integer movieId) {
		movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
		movieRepository.deleteById(movieId);
		return !movieRepository.existsById(movieId);
	}

	public Movie findById(Integer movieId) {
		return movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
	}

	public List<Movie> findAll() {
		return movieRepository.findAll();
	}
}
