package com.tech_symfony.movie_booking.api.user.register;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtEncoder jwtEncoder;

	private final JwtDecoder jwtDecoder;

	public String generateToken(String subject) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
			.issuer("self")
			.issuedAt(now)
			.expiresAt(now.plus(5, ChronoUnit.MINUTES))
			.subject(subject)
			.build();

		JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256).build();
		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}

	public boolean isTokenExpired(String token) {
		try {
			Jwt decodedJwt = jwtDecoder.decode(token);
			Instant expiresAt = decodedJwt.getExpiresAt();
			return Objects.requireNonNull(expiresAt).isBefore(Instant.now());
		} catch (JwtException e) {
			return true;
		}
	}

	public String extractSubject(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return jwt.getSubject();
	}
}
