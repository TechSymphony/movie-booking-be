package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.api.user.Gender;
import com.tech_symfony.movie_booking.api.user.User;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.time.LocalDateTime;

@Projection(name = "permissionInfo", types = {Permission.class})
interface PermissionProjector {

	Integer getId();

	String getName();


}
