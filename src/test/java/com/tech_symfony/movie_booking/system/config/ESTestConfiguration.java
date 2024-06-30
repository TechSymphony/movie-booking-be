package com.tech_symfony.movie_booking.system.config;

import com.tech_symfony.movie_booking.system.container.ElasticTestContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@TestConfiguration
public class ESTestConfiguration extends ElasticsearchConfiguration {

	@Override
	public ClientConfiguration clientConfiguration() {

		ElasticTestContainer elasticTestContainer = new ElasticTestContainer();
		elasticTestContainer.start();

		return ClientConfiguration.builder()
			.connectedTo("localhost:9200")
			.withBasicAuth("elastic", "default")
			.build();
	}
}
