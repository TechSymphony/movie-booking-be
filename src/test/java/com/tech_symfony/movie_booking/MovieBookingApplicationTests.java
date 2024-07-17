package com.tech_symfony.movie_booking;

import com.tech_symfony.movie_booking.system.config.ESTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ESTestConfiguration.class)
class MovieBookingApplicationTests {

	@Test
	void contextLoads() {

	}

}
