package com.tech_symfony.movie_booking.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
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
			name = "Duy Nguyen ",
			email = "contact.duynguyen@gmail.com",
			url = "https://github.com/1119-DuyNguyen"
		),
		description = "Open Api for  movie booking app",
		title = "OpenApi movie booking - Duy Nguyen",
		version = "1.0"
	)

)
public class SwaggerConfiguration {
}
