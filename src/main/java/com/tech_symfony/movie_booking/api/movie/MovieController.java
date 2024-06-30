package com.tech_symfony.movie_booking.api.movie;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RepositoryRestController
@ResponseBody
public class MovieController {

	private final MovieService movieService;
	private final RepositoryEntityLinks entityLinks;
	private final MovieModelAssembler movieModelAssembler;

	@Operation(
		summary = "Thêm mới một bộ phim",
		description = "API thêm mới một bộ phim"
	)
	@PostMapping(value = "/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EntityModel<Movie>> create(
		@Valid @RequestBody MovieDTO dataRaw
	) {
		Movie newMovie = movieService.create(dataRaw);
		Link link = entityLinks.linkToItemResource(Movie.class, newMovie.getId());
		return ResponseEntity
			.created(link.toUri())
			.body(movieModelAssembler.toModel(newMovie));
	}

	@Operation(
		summary = "Cập nhật một bộ phim",
		description = "API cập nhật một bộ phim"
	)
	@PutMapping(value = "/movies/{movieId}")
	public ResponseEntity<EntityModel<Movie>> update(
		@PathVariable Integer movieId,
		@Valid @RequestBody MovieDTO dataRaw
	) {
		Movie newMovie = movieService.update(movieId, dataRaw);
		return ResponseEntity
			.ok(movieModelAssembler.toModel(newMovie));
	}
}
