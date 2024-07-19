package com.tech_symfony.movie_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication
//@EnableJpaRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JpaRepository.class))
//@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ElasticsearchRepository.class))
public class MovieBookingApplication {

	public static void main(String[] args) {

		SpringApplication.run(MovieBookingApplication.class, args);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//default password

		String encodedPassword = encoder.encode("123");
		System.out.println(UUID.randomUUID().toString());

		System.out.println(encodedPassword + " " + encoder.matches("123", encodedPassword));
	}

}
