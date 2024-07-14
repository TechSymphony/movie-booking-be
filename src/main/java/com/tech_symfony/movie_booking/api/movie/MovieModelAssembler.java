package com.tech_symfony.movie_booking.api.movie;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {
	@Override
	public EntityModel<Movie> toModel(Movie entity) {

		return EntityModel.of(
			entity,
			linkTo(methodOn(MovieController.class).getMovieById(entity.getId())).withSelfRel(),
			linkTo(methodOn(MovieController.class).getAllMovies()).withRel("movies")
		);
	}
}
