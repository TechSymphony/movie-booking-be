package com.tech_symfony.movie_booking.system.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}

