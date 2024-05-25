package com.tech_symfony.movie_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MovieBookingApplication {

	public static void main(String[] args) {

		SpringApplication.run(MovieBookingApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//default password
		String encodedPassword = encoder.encode("123");
		System.out.println(encodedPassword + " " + encoder.matches("123", encodedPassword));
	}

}
