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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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
		when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());
		when(movieGenreRepository.findById(anyInt())).thenReturn(Optional.of(new MovieGenre()));

		Movie result = movieService.create(movieDTO);
		assertNotNull(result);

		movieDTO.setShowtimes(null);
		movieDTO.setGenres(null);
		result = movieService.create(movieDTO);
		assertNotNull(result);
		verify(movieRepository, times(4)).save(any(Movie.class));
	}

	@Test
	void testUpdateMovie() {
		Movie movie = new Movie();
		when(movieRepository.findById(anyInt())).thenReturn(Optional.of(movie));
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);
		when(movieGenreRepository.findById(anyInt())).thenReturn(Optional.of(new MovieGenre()));

		Movie result = movieService.update(1, movieDTO);
		assertNotNull(result);

		movieDTO.setShowtimes(null);
		movieDTO.setGenres(null);
		result = movieService.update(1, movieDTO);
		assertNotNull(result);

		verify(movieRepository, times(2)).save(any(Movie.class));
	}

	@Test
	void testCheckGenresWithEmpty() {
		when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());
		movieDTO.setGenres(Set.of());

		ForbidenMethodControllerException exception = assertThrows(ForbidenMethodControllerException.class, () -> {
			movieService.create(movieDTO);
		});
		assertEquals("Genres data not found", exception.getMessage());
	}

	@Test
	void testCheckGenresWithNonExistentGenre() {
		when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			movieService.create(movieDTO);
		});
		assertThat(exception.getMessage(), containsString("Not found genre with id: "));
	}

	@Test
	void testDeleteMovie() {
		given(movieRepository.findById(anyInt())).willReturn(Optional.of(new Movie()));
		movieService.delete(1);
		verify(movieRepository).deleteById(1);
	}

	@Test
	void testGetMovie() {
		given(movieRepository.findById(anyInt())).willReturn(Optional.of(new Movie()));
		assertNotNull(movieService.findById(1));
		verify(movieRepository).findById(1);
	}

	@Test
	void testGetMovies() {
		given(movieRepository.findAll()).willReturn(List.of(new Movie()));
		assertNotNull(movieService.findAll());
		verify(movieRepository).findAll();
	}
}
