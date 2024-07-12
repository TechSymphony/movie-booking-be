package com.tech_symfony.movie_booking.api.movie;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@BasePathAwareController
@ResponseBody
public class MovieController {

	private final MovieService movieService;
	private final MovieModelAssembler movieModelAssembler;

	@Operation(
		summary = "Xem danh sách bộ phim",
		description = "API xem danh sách bộ phim"
	)
	@GetMapping(value = "/movies")
	public CollectionModel<EntityModel<MovieDTO>> getAllMovies() {
		return movieModelAssembler.toCollectionModel(movieService.findAll());
	}

	@Operation(
		summary = "Xem một bộ phim",
		description = "API xem một bộ phim"
	)
	@GetMapping(value = "/movies/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<MovieDTO> getMovieById(
		@PathVariable Integer movieId
	) {
		return movieModelAssembler.toModel(movieService.findById(movieId));
	}

	@Operation(
		summary = "Thêm mới một bộ phim",
		description = "API thêm mới một bộ phim"
	)
	@PostMapping(value = "/movies")
	@ResponseStatus(HttpStatus.CREATED)
	public EntityModel<MovieDTO> create(
		@Valid @RequestBody MovieDTO dataRaw
	) {
		return movieModelAssembler.toModel(movieService.create(dataRaw));
	}

	@Operation(
		summary = "Cập nhật một bộ phim",
		description = "API cập nhật một bộ phim"
	)
	@PutMapping(value = "/movies/{movieId}")
	public EntityModel<MovieDTO> update(
		@PathVariable Integer movieId,
		@Valid @RequestBody MovieDTO dataRaw
	) {
		return movieModelAssembler.toModel(movieService.update(movieId, dataRaw));
	}

	@Operation(
		summary = "Xóa một bộ phim",
		description = "API xóa một bộ phim"
	)
	@DeleteMapping(value = "/movies/{movieId}")
	public ResponseEntity<?> delete(
		@PathVariable Integer movieId
	) {
		movieService.delete(movieId);
		return ResponseEntity
			.noContent()
			.build();
	}
}
