package com.tech_symfony.movie_booking.api.showtime;

import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.model.NamedProjection;

import java.sql.Date;
import java.sql.Time;

public interface ShowtimeProjection {

	Date getStartDate();

	Time getStartTime();

	Integer getRunningTime();

	Boolean getStatus();

	NamedProjection getMovie();

	NamedProjection getRoom();

}
