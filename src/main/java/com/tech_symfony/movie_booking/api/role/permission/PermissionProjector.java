package com.tech_symfony.movie_booking.api.role.permission;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "permissionInfo", types = {Permission.class})
interface PermissionProjector {

	Integer getId();

	String getName();


}
