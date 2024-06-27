package com.tech_symfony.movie_booking.api.movie_genre;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource(path = "movie-genres")
public interface MovieGenreRepository extends BaseAuthenticatedRepository<MovieGenre, Integer> {

	@PreAuthorize("hasAuthority( 'SAVE_MOVIE_GENRE')")
	@Override
	MovieGenre save(MovieGenre movieGenre);

	@PreAuthorize("hasAuthority( 'DELETE_MOVIE_GENRE')")
	@Override
	void deleteById(Integer id);
}
