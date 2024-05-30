package com.tech_symfony.movie_booking.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.MetadataConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class ApiRestConfiguration implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(
		RepositoryRestConfiguration config, CorsRegistry cors
	) {
//		config.disableDefaultExposure();
		config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
//		config.setExposeRepositoryMethodsByDefault(false);
//		config.getMetadataConfiguration().setAlpsEnabled(false);
	}
}
