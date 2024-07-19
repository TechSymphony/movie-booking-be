package com.tech_symfony.movie_booking.system.config.auth;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.util.StringUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;


@Configuration
public class AuthorizationServerConfig {
	@Value("${BASE_URL:}")
	String BASE_URL;
	@Value("${FRONTEND_URL:}")
	String FRONTEND_URL;

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		if (StringUtils.isEmpty(FRONTEND_URL)) {
			FRONTEND_URL = "http://localhost:3000" + "/brower-callback";
		}
		RegisteredClient publicClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("public-client")
			.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			.redirectUri("https://oauth.pstmn.io/v1/browser-callback")
			.redirectUri(BASE_URL + "/swagger-ui/oauth2-redirect.html")
			// for frontend
			.redirectUri(FRONTEND_URL)

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

	@Bean
	public JWKSource<SecurityContext> jwkSource(
		@Value("${jwt.key.id}") String id,
		@Value("${jwt.key.private}") RSAPrivateKey privateKey,
		@Value("${jwt.key.public}") RSAPublicKey publicKey) {
		var rsa = new RSAKey.Builder(publicKey)
			.privateKey(privateKey)
			.keyID(id)
			.build();
		var jwk = new JWKSet(rsa);
		return new ImmutableJWKSet<>(jwk);
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
		return (context) -> {
			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
				context.getClaims().claims((claims) -> {
					Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities());
					claims.put("scope", roles);
				});
			}
		};
	}

	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}


}
