package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.utils.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class JwtIntegrationTest {

	@Autowired
	private JwtService jwtService;

	private UserDetails userDetails;

	@BeforeEach
	public void setUp() {
		userDetails = User.withUsername("testuser")
			.password("password")
			.roles("USER")
			.build();
	}

	@Test
	public void testGenerateAccessToken() {
		String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);
		assertThat(token).isNotNull();
		String subject = Jwts.parserBuilder()
			.setSigningKey(jwtService.getSignKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
		assertThat(subject).isEqualTo("testuser");
	}

	@Test
	public void testGenerateRefreshToken() {
		String token = jwtService.generateRefreshToken(new HashMap<>(), userDetails);
		assertThat(token).isNotNull();
		String subject = Jwts.parserBuilder()
			.setSigningKey(jwtService.getSignKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
		assertThat(subject).isEqualTo("testuser");
	}

	@Test
	public void testExtractSubject() {
		String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);
		String subject = jwtService.extractSubject(token);
		assertThat(subject).isEqualTo("testuser");
	}

	@Test
	public void testIsTokenValid() {
		String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);
		boolean isValid = jwtService.isTokenValid(token, userDetails);
		assertThat(isValid).isTrue();
	}

	@Test
	public void testIsTokenExpired() {
		// Generate a token with a very short expiration time for testing
		String token = Jwts.builder()
			.setSubject(userDetails.getUsername())
			.setExpiration(new Date(System.currentTimeMillis() - 60000)) // already expired
			.signWith(jwtService.getSignKey(), SignatureAlgorithm.HS256)
			.compact();
		assertThatThrownBy(() -> jwtService.isTokenExpired(token))
			.isInstanceOf(ExpiredJwtException.class)
			.hasMessageContaining("JWT expired");

	}
}
