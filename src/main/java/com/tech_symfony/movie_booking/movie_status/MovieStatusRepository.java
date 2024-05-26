package com.tech_symfony.movie_booking.movie_status;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface MovieStatusRepository extends BaseAuthenticatedRepository<MovieStatus, Integer> {


}
