package com.tech_symfony.movie_booking.api.user.register;


public class UnverifiedUserException extends RuntimeException {
	public UnverifiedUserException() {
		super("User is not verified");
	}
}
