package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@RepositoryRestResource
public interface BillRepository extends BaseAuthenticatedRepository<Bill, UUID> {

	@Override
	@RestResource(exported = false)
	<S extends Bill> S save(S entity);

	@Override
	@RestResource(exported = false)
	void delete(Bill entity);
}
