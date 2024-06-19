package com.tech_symfony.movie_booking.system.exception;

public class RoleNotFoundException extends RuntimeException{
	public RoleNotFoundException(Integer id){super("Could not find role " + id);}
}
