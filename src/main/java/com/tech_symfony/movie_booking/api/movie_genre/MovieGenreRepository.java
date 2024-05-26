package com.tech_symfony.movie_booking.api.movie_genre;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "movie-genres")
public interface MovieGenreRepository extends BaseAuthenticatedRepository<MovieGenre, Integer> {
}
