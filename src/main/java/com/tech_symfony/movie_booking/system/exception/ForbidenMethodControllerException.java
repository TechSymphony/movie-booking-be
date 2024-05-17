package com.tech_symfony.movie_booking.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenMethodControllerException extends RuntimeException {
	public ForbidenMethodControllerException(String message) {
		super(message);
	}
}
