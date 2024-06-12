package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.user.Gender;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.time.LocalDateTime;

@Projection(name = "roleInfo", types = {Role.class})
interface RoleInfoProjector {

	String getName();


}
