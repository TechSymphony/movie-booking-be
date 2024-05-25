package com.tech_symfony.movie_booking.system.config;

import com.tech_symfony.movie_booking.user.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {
	private final CustomUserDetailService customUserDetailService;

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
		throws Exception {
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
			.oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0

//		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//		http
//			.getConfigurer(authorizationServerConfigurer);
//		authorizationServerConfigurer
//			.authorizationEndpoint(authorizationEndpoint ->
//				authorizationEndpoint
//
//					.consentPage("/oauth2/v1/authorize")
//			);

		http
			// Redirect to the login page when not authenticated from the
			// authorization endpoint
			.exceptionHandling((exceptions) -> exceptions
				.defaultAuthenticationEntryPointFor(
					new LoginUrlAuthenticationEntryPoint("/login"),
					new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
				)
			)

			// Accept access tokens for User Info and/or Client Registration
			.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()));

		return http.cors(Customizer.withDefaults()).build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
		throws Exception {
		http
			// Authorize requests
			.authorizeHttpRequests((authorize) -> authorize

				.requestMatchers("/", "api/v1/landing/**", "api/v1/auth/**").permitAll()

				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
				.anyRequest().authenticated()
			)
			.userDetailsService(customUserDetailService)
			// Form login handles the redirect to the login page from the
			// authorization server filter chain
			.oauth2Login(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.cors(Customizer.withDefaults()).build();
	}


	//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails = User.withDefaultPasswordEncoder()
//			.username("user")
//			.password("123")
//			.roles("USER")
//			.build();
//
//		return new InMemoryUserDetailsManager(userDetails);
//	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);

		return source;
	}


}
