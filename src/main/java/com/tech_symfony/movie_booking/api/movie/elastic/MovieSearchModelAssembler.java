package com.tech_symfony.movie_booking.api.movie.elastic;

import com.tech_symfony.movie_booking.api.movie.Movie;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieSearchModelAssembler implements RepresentationModelAssembler<MovieSearch, EntityModel<MovieSearch>> {

	private final RepositoryEntityLinks entityLinks;

	@Override
	public EntityModel<MovieSearch> toModel(MovieSearch movieSearch) {
		Link linkResource = entityLinks.linkToItemResource(Movie.class, movieSearch.getId());
		Link linkCollectionResource = entityLinks.linkToCollectionResource(Movie.class);

		return EntityModel.of(
			movieSearch,
			linkResource,
			linkCollectionResource
		);
	}
}
