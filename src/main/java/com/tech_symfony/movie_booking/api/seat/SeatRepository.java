package com.tech_symfony.movie_booking.api.seat;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface SeatRepository extends BaseAuthenticatedRepository<Seat, Integer> {

}
