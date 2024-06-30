package com.tech_symfony.movie_booking.api.user;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}

