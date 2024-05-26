package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;


@RepositoryRestResource
public interface MovieRepository extends BaseAuthenticatedRepository<Movie, Integer> {

	@PreAuthorize("true")
	@RestResource(path = "slug")
	Page<Movie> findBySlugContaining(@Param("slug") String slug, Pageable pageable);


}
