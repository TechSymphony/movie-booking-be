package com.tech_symfony.movie_booking.api;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RootController {
	private final RepositoryEntityLinks entityLinks;

	@Operation(
		summary = "Đường dẫn gốc",
		description = "Từ đường dẫn này sẽ đi đến những đường dẫn khác dựa theo hành vi người dùng"
	)
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
