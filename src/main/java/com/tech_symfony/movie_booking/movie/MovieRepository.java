package com.tech_symfony.movie_booking.movie;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface MovieRepository extends BaseAuthenticatedRepository<Movie, Integer> {

	@RestResource(path = "slug")
	Page<Movie> findBySlugContaining(@Param("slug") String slug, Pageable pageable);


}
