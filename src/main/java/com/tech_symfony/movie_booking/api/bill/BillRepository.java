package com.tech_symfony.movie_booking.api.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, UUID> {


}
