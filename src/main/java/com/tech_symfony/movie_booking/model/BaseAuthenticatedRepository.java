package com.tech_symfony.movie_booking.model;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@SecurityRequirement(name = "security_auth")
@NoRepositoryBean
public interface BaseAuthenticatedRepository<T, ID> extends JpaRepository<T, ID> {
}
