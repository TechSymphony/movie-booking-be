package com.tech_symfony.movie_booking.system.container;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
public class BaseTestContainer {

	@Container
	protected static ElasticsearchContainer elasticsearchContainer = new ElasticTestContainer();

	@BeforeAll
	static void setUp() {
		elasticsearchContainer.stop();
		elasticsearchContainer.start();
	}

	@AfterAll
	static void destroy() {
		// runs after every class
		elasticsearchContainer.stop();
	}

}
