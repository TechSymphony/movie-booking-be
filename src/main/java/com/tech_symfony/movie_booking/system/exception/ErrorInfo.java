package com.tech_symfony.movie_booking.system.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ErrorInfo {
	public final String className;
	public final String timestamp;
	private List<Object> errors;

	public ErrorInfo(Exception ex) {
		this.className = ex.getClass().getName();
		this.errors = new ArrayList<>();
		this.errors.add(new HashMap<String, String>().put("exMessage", ex.getLocalizedMessage()));
		this.timestamp = String.valueOf(System.currentTimeMillis());
	}

	public ErrorInfo(Exception ex, List<Object> errors) {
		this.className = ex.getClass().getName();
		this.errors = errors;
		this.timestamp = String.valueOf(System.currentTimeMillis());
	}
}
