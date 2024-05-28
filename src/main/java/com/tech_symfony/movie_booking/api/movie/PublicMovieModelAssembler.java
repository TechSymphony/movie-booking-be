package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.order.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class PublicMovieModelAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

	@Override
	public EntityModel<Movie> toModel(Movie movie) {

		// Unconditional links to single-item resource and aggregate root

		EntityModel<Movie> movieModel = EntityModel.of(movie,
//			linkTo(methodOn(OrderController.class).one(movie.getId())).withSelfRel(),
			linkTo(methodOn(OrderController.class).all()).withRel("orders"));

		// Conditional links based on state of the order

//		if (movie.getStatus() == OrderStatus.IN_PROGRESS) {
//			movieModel.add(linkTo(methodOn(OrderController.class).cancel(movie.getId())).withRel("cancel"));
//			movieModel.add(linkTo(methodOn(OrderController.class).complete(movie.getId())).withRel("complete"));
//		}

		return movieModel;
	}

}
