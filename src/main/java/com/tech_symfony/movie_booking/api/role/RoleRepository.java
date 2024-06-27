package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.role.permission.Permission;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@Tag(name = "Role Resource", description = "Role API")
public interface RoleRepository extends BaseAuthenticatedRepository<Role, Integer> {
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.role.id = :roleId")
	Boolean existsByUsersRole(@Param("roleId") Integer roleId);


}
