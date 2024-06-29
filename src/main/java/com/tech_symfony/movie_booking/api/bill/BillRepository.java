package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(excerptProjection = BillInfoProjection.class)
@PreAuthorize("hasAuthority( 'READ_BILL')")
public interface BillRepository extends BaseAuthenticatedRepository<Bill, UUID> {


	@Override
	@RestResource(exported = false)
	void delete(Bill entity);

	@RestResource(exported = false)
	Optional<Bill> findByIdAndUser(UUID uuid, User user);
}
