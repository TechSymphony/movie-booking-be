package com.tech_symfony.movie_booking.api.user;


public class UnverifiedUserException extends RuntimeException {
    public UnverifiedUserException() {
        super("User is not verified");
    }
}
