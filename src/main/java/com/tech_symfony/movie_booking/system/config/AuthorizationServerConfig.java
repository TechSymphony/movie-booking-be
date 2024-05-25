package com.tech_symfony.movie_booking.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.time.Duration;
import java.util.UUID;


@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

	@Bean
	public RegisteredClientRepository registeredClientRepository() {

		RegisteredClient publicClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("public-client")
			.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.redirectUri("https://oauth.pstmn.io/v1/browser-callback")
			.scope(OidcScopes.OPENID)
			.scope(OidcScopes.PROFILE)
			.tokenSettings(TokenSettings.builder()
				.accessTokenTimeToLive(Duration.ofMinutes(1000))
				.refreshTokenTimeToLive(Duration.ofDays(7))
				.build())
			.clientSettings(ClientSettings.builder()
				.requireAuthorizationConsent(false)
				.requireProofKey(true)
				.build()
			)
			.build();

		return new InMemoryRegisteredClientRepository(publicClient);
	}


}
