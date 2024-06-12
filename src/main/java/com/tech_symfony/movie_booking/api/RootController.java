package com.tech_symfony.movie_booking.api;

import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.api.movie.MovieRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class RootController {
	private final RepositoryEntityLinks entityLinks;

	@GetMapping
	RepresentationModel<?> index() {

		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(Link.of("/login", "login"));
		rootModel.add(entityLinks.linkToCollectionResource(Movie.class));
		rootModel.add(entityLinks.linkToCollectionResource(Cinema.class));
		rootModel.add(entityLinks.linkToCollectionResource(Showtime.class));


		return rootModel;
	}

}
