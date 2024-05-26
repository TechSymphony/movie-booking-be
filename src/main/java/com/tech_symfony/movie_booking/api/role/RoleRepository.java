package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends BaseAuthenticatedRepository<Role, Integer> {
}
