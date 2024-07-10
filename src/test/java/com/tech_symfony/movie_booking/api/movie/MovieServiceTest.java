package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.*;

public class MovieServiceTest extends BaseUnitTest {

	@InjectMocks
	private DefaultMovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ShowtimeRepository showtimeRepository;

	@Mock
	private MovieGenreRepository movieGenreRepository;

	private MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

	private MovieDTO movieDTO;

	@BeforeEach
	void setUp() {
		movieDTO = new MovieDTO();

		movieDTO.setName("The Godfather");
		movieDTO.setSubName("The Godfather");
		movieDTO.setDirector("Francis Ford Coppola");
		movieDTO.setCaster("Mario Puzo");
		movieDTO.setReleaseDate("1972-03-24");
		movieDTO.setEndDate("1972-03-24");
		movieDTO.setRunningTime(175);
		movieDTO.setLanguage("English");
		movieDTO.setNumberOfRatings(100);
		movieDTO.setSumOfRatings(1000);
		movieDTO.setDescription("The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.");
		movieDTO.setPoster("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
		movieDTO.setTrailer("https://www.youtube.com/watch?v=sY1S34973zA");
		movieDTO.setRated(5);
		movieDTO.setSlug("the-godfather");

		movieDTO.setShowtimes(Set.of(1, 2));
		movieDTO.setGenres(Set.of(1, 2));
	}

	@Test
	void testCreateMovie() {
		Movie movie = new Movie();
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);
		when(showtimeRepository.existsById(anyInt())).thenReturn(true);
		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(new Showtime()));
		when(movieGenreRepository.existsById(anyInt())).thenReturn(true);
		when(movieGenreRepository.findById(anyInt())).thenReturn(Optional.of(new MovieGenre()));

		Movie result = movieService.create(movieDTO);

		assertNotNull(result);
		verify(movieRepository).save(any(Movie.class));
	}

	@Test
	void testUpdateMovie() {
		Movie movie = new Movie();
		when(movieRepository.findById(anyInt())).thenReturn(Optional.of(movie));
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);
		when(showtimeRepository.existsById(anyInt())).thenReturn(true);
		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(new Showtime()));
		when(movieGenreRepository.existsById(anyInt())).thenReturn(true);
		when(movieGenreRepository.findById(anyInt())).thenReturn(Optional.of(new MovieGenre()));

		Movie result = movieService.update(1, movieDTO);

		assertNotNull(result);
		verify(movieRepository).save(any(Movie.class));
	}

	@Test
	void testCheckShowtimesWithNull() {
		movieDTO.setShowtimes(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Showtimes field not found", exception.getMessage());
	}

	@Test
	void testCheckShowtimesWithEmpty() {
		movieDTO.setShowtimes(new HashSet<>());

		ForbidenMethodControllerException exception = assertThrows(ForbidenMethodControllerException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Showtimes data not found", exception.getMessage());
	}

	@Test
	void testCheckShowtimesWithNonExistentShowtime() {
		when(showtimeRepository.existsById(anyInt())).thenReturn(false);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Showtime not found", exception.getMessage());
	}

	@Test
	void testCheckGenresWithNull() {
		when(showtimeRepository.existsById(anyInt())).thenReturn(true);
		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(new Showtime()));
		movieDTO.setGenres(null);
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Genres field not found", exception.getMessage());
	}

	@Test
	void testCheckGenresWithEmpty() {
		when(showtimeRepository.existsById(anyInt())).thenReturn(true);
		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(new Showtime()));
		movieDTO.setGenres(Set.of());

		ForbidenMethodControllerException exception = assertThrows(ForbidenMethodControllerException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Genres data not found", exception.getMessage());
	}

	@Test
	void testCheckGenresWithNonExistentGenre() {
		when(showtimeRepository.existsById(anyInt())).thenReturn(true);
		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(new Showtime()));

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Movie genre not found", exception.getMessage());
	}
}
