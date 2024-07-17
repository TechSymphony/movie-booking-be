package com.tech_symfony.movie_booking.system.container;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.*;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ExtendWith(SpringExtension.class)
public class BaseTestContainer {

	@Container
	protected final static ElasticsearchContainer elasticsearchContainer = new ElasticTestContainer();
	@Container
	private final static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8")
		.withDatabaseName("testcontainer")
		.withUsername("user")
		.withPassword("pass");

	@DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);

	}

	private static void openContainer() {
		elasticsearchContainer.start();
		mySQLContainer.start();

	}

	private static void closeContainer() {
		mySQLContainer.stop();
		elasticsearchContainer.stop();

	}

	@BeforeAll
	static void setUp() {
		closeContainer();
		openContainer();
	}

	@AfterAll
	static void destroy() {
		closeContainer();

	}


}
