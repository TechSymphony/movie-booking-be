package com.tech_symfony.movie_booking.system.exception;

public class RoleInUseException extends RuntimeException {
	public RoleInUseException(Integer roleId) {
		super("Role with id " + roleId + " is in use and cannot be deleted.");
	}
}
