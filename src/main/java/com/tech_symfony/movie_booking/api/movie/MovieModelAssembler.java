package com.tech_symfony.movie_booking.api.movie;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

	private final RepositoryEntityLinks entityLinks;

	@Override
	public EntityModel<Movie> toModel(Movie entity) {

		Link linkResource = entityLinks.linkToItemResource(Movie.class, entity.getId());
		Link linkCollectionResource = entityLinks.linkToCollectionResource(Movie.class);

		return EntityModel.of(
			entity,
			linkResource,
			linkCollectionResource
		);
	}
}
