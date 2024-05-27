package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Tag(name = "Role Resource", description = "Role API")
public interface RoleRepository extends BaseAuthenticatedRepository<Role, Integer> {
}
