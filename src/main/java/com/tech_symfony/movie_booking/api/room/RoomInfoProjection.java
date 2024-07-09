package com.tech_symfony.movie_booking.api.room;

import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.api.user.attribute.Gender;
import com.tech_symfony.movie_booking.model.NamedProjection;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.time.LocalDateTime;

@Projection(name = "roomProjection", types = {Room.class})
interface RoomInfoProjection extends NamedProjection {


	Integer getTotalSeats();

	Integer getAvailableSeats();


}
