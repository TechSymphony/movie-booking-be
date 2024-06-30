package com.tech_symfony.movie_booking.system.config.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
	name = "security_auth",
	type = SecuritySchemeType.OAUTH2,
	flows = @OAuthFlows(authorizationCode =
	@OAuthFlow(
		authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}"
		, tokenUrl = "${springdoc.oAuthFlow.tokenUrl}")))
@Configuration
@OpenAPIDefinition(
	info = @Info(
		contact = @Contact(
			name = "tech symphony",
			email = "contact.duynguyen@gmail.com",
			url = "https://github.com/TechSymphony/movie-booking-be"
		),
		description = "Open Api for  movie booking app",
		title = "OpenApi movie booking ",
		version = "1.0"
	)

)
public class SwaggerConfiguration {

//	@Bean
//	SpringDocConfiguration springDocConfiguration() {
//		return new SpringDocConfiguration();
//	}
//
//	@Bean
//	SpringDocConfigProperties springDocConfigProperties() {
//		return new SpringDocConfigProperties();
//	}
//
//	@Bean
//	ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
//		return new ObjectMapperProvider(springDocConfigProperties);
//	}
//
//	@Bean
//	SpringDocUIConfiguration SpringDocUIConfiguration(Optional<SwaggerUiConfigProperties> optionalSwaggerUiConfigProperties) {
//		return new SpringDocUIConfiguration(optionalSwaggerUiConfigProperties);
//	}
}
