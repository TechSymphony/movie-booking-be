package com.tech_symfony.movie_booking.api.movie.exception;

import java.sql.Timestamp;
import java.util.List;

public class MovieNotFoundException extends RuntimeException {
	private final Timestamp timestamp;
	private final String httpStatus;
	private final int statusCode;
	private final String error;
	private final List<String> messages;

	public MovieNotFoundException(Integer id) {
		super("Could not find movie " + id);
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.httpStatus = "NOT_FOUND";
		this.statusCode = 404;
		this.error = "Movie Not Found";
		this.messages = List.of("Could not find movie " + id);
	}
}
