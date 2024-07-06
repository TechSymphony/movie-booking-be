package com.tech_symfony.movie_booking.api.showtime;

import java.sql.Date;
import java.sql.Time;

public interface ShowtimeProjection {

	Date getStartDate();

	Time getStartTime();

	Integer getRunningTime();

	Boolean getStatus();

	String getMovieTitle();

	String getRoomName();

}
