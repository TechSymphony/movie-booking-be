package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "Role Resource", description = "Role API")
@RepositoryRestResource(excerptProjection = RoleInfoProjector.class)
public interface RoleRepository extends BaseAuthenticatedRepository<Role, Integer> {

}
