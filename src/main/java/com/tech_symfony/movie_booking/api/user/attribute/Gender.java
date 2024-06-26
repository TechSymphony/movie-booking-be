package com.tech_symfony.movie_booking.api.user.attribute;

import lombok.Getter;

@Getter
public enum Gender {
	MALE("Nam"),
	FEMALE("Nữ"),
	UNKNOWN("Khác");

	private final String value;

	Gender(String value) {
		this.value = value;
	}

}
