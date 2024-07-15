package com.tech_symfony.movie_booking.system.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

@Configuration
public class ProjectionConfiguration {

	@Bean
	public SpelAwareProxyProjectionFactory projectionFactory() {
		return new SpelAwareProxyProjectionFactory();
	}

}
