package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenreRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieServiceTest extends BaseUnitTest {

	@InjectMocks
	private DefaultMovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ShowtimeRepository showtimeRepository;

	@Mock
	private MovieGenreRepository movieGenreRepository;

	@Test
	public void shouldCreateMovieSuccessfully() {
		MovieDTO movieDTO = new MovieDTO();

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

		// Arrays to Sets
		movieDTO.setShowtimes(Sets.newHashSet(Arrays.asList(1, 2)));
		movieDTO.setGenres(Sets.newHashSet(Arrays.asList(1, 2, 3)));

		Showtime st1 = new Showtime();
		st1.setId(1);
		Showtime st2 = new Showtime();
		st2.setId(2);

		List<Showtime> showtimeList = Arrays.asList(st1, st2);

		MovieGenre mg1 = new MovieGenre();
		mg1.setId(1);
		MovieGenre mg2 = new MovieGenre();
		mg2.setId(2);
		MovieGenre mg3 = new MovieGenre();
		mg3.setId(3);

		List <MovieGenre> genreList = Arrays.asList(mg1, mg2, mg3);

		// Given

		given(movieRepository.save(any(Movie.class))).willReturn(new Movie());
		given(showtimeRepository.existsById(any(Integer.class))).willReturn(true);
		given(movieGenreRepository.existsById(any(Integer.class))).willReturn(true);
		given(showtimeRepository.findById(any(Integer.class))).willReturn(Optional.of(new Showtime()));
		given(movieGenreRepository.findById(any(Integer.class))).willReturn(Optional.of(new MovieGenre()));

		// When

		Movie movie = movieService.create(movieDTO);

		// Then

		assertNotNull(movie);
	}
}
