package com.tech_symfony.movie_booking;

import com.tech_symfony.movie_booking.system.config.ESTestConfiguration;
import com.tech_symfony.movie_booking.system.container.ElasticTestContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@SpringBootTest
@Import(ESTestConfiguration.class)
class MovieBookingApplicationTests {

	@Test
	void contextLoads() {

	}

}
