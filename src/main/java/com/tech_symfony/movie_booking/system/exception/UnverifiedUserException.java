package com.tech_symfony.movie_booking.system.exception;


public class UnverifiedUserException extends RuntimeException {
    public UnverifiedUserException() {
        super("User is not verified");
    }
}
