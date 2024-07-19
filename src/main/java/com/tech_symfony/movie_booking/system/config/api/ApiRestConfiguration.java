package com.tech_symfony.movie_booking.system.config.api;

import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.ticket.Ticket;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class ApiRestConfiguration implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(
		RepositoryRestConfiguration config, CorsRegistry cors
	) {

//		config.disableDefaultExposure();
		config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
		ExposureConfiguration exposureConfiguration = config.getExposureConfiguration();
		exposureConfiguration.forDomainType(Ticket.class)
			.withItemExposure(((metadata, httpMethods)
				-> httpMethods.disable(HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE)
			))
			.withCollectionExposure(((metadata, httpMethods)
				-> httpMethods.disable(HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.DELETE)
			));
		//		config.setExposeRepositoryMethodsByDefault(false);
//		config.getMetadataConfiguration().setAlpsEnabled(false);
	}
}
