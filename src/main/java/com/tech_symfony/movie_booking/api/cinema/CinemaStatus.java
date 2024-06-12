package com.tech_symfony.movie_booking.api.cinema;

import lombok.Getter;

@Getter
public enum CinemaStatus {
	OPENING("Hoạt động"),
	CLOSED("Đóng cửa"),
	MAINTAINED("Đang bảo trì");

	private final String value;

	CinemaStatus(String value) {
		this.value = value;
	}

}
