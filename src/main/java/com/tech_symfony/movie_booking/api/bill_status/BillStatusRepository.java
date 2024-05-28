package com.tech_symfony.movie_booking.api.bill_status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillStatusRepository extends JpaRepository<BillStatus, Integer> {
}
