package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface MovieRepository extends BaseAuthenticatedRepository<Movie, Integer> {

	@RestResource(path = "public", rel = "public")
	@Query("SELECT m FROM Movie m WHERE (:slug is null or m.slug like %:slug% ) ")
	Page<Movie> findBySlugContaining(@Param("slug") String slug, Pageable pageable);

	@Override
	List<Movie> findAll();

	@Override
	Optional<Movie> findById(Integer id);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'SAVE_MOVIE')")
	@Override
	Movie save(Movie movie);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'DELETE_MOVIE')")
	@Override
	void deleteById(Integer id);


}
