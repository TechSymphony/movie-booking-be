package com.tech_symfony.movie_booking.api.movie.elastic;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RequiredArgsConstructor
@RepositoryRestController
@ResponseBody
public class MovieElasticController {

	private final MovieElasticService movieElasticService;
	private final MovieSearchModelAssembler movieSearchModelAssembler;

	@Operation(
		summary = "Tìm kiếm movie dựa theo các tham số truyền vào",
		description = "API tìm kiếm thông tin movie dựa theo các thông tin được truyền vào."
	)
	@GetMapping("/movies/search")
	@ResponseBody
	public CollectionModel<EntityModel<MovieSearch>> search(Pageable pageable, @RequestParam Optional<String> name) {

		Page<MovieSearch> movies = movieElasticService.search(PageRequest.of(
			pageable.getPageNumber(),
			pageable.getPageSize()
		), name);
		return movieSearchModelAssembler.toCollectionModel(movies);
	}
}
