package com.tech_symfony.movie_booking.order;

class OrderNotFoundException extends RuntimeException {

	OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
