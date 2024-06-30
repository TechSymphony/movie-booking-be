package com.tech_symfony.movie_booking.system.container;

import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.time.Duration;

public class ElasticTestContainer extends ElasticsearchContainer {
	private static final String DOCKER_ELASTIC = "docker.elastic.co/elasticsearch/elasticsearch:8.3.3";

	private static final String CLUSTER_NAME = "sample-cluster";

	private static final String ELASTIC_SEARCH = "elasticsearch";

	public ElasticTestContainer() {
		super(DOCKER_ELASTIC);
		this.addFixedExposedPort(9200, 9200);
		this.addFixedExposedPort(9300, 9300);
		this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);

		this.getEnvMap().put("xpack.security.enabled", "true");
		this.getEnvMap().put("xpack.security.http.ssl.enabled", "false");
		this.getEnvMap().put("xpack.security.transport.ssl.enabled", "false");
		this.addEnv("ES_JAVA_OPTS", "-Xms256m -Xmx256m");
		this.withPassword("default");

		this.setWaitStrategy(new HttpWaitStrategy()
			.forPort(9200)
			.withBasicCredentials("elastic", "default")
			.withStartupTimeout(Duration.ofSeconds(60)));
	}
}
