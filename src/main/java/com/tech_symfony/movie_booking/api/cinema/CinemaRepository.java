package com.tech_symfony.movie_booking.api.cinema;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface CinemaRepository extends BaseAuthenticatedRepository<Cinema, Integer> {

}
