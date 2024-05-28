package com.tech_symfony.movie_booking.api.seat_type;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface SeatTypeRepository extends BaseAuthenticatedRepository<SeatType, Integer> {

}
