package com.tech_symfony.movie_booking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {

//	@Value("${jwt.secret-key}")
	private final String secretKey = "281df2ff6d0b179f11411c13d507d0709ee13b4b651f2c390283666d9561e6b5a6b653eca47f3bca40809d92995b3c9483d9b45b68e5644e25728b880fa4d25f1b11703164575406b5b625fda2796d66ea8d1d169240efb001f06fb34e0786e0595f7013e87d78239cc4285dbb1264262f65ef9a7259cdccc4332c8b1a8ea9c06c99408c604428285543d1ba1e6caf22de4077a3e8e9a6715463d0b23ffd8e816fc86643cb3441cbe97779dee61be4a3b10edac712e531bb212679465c06e72b799d3afa837e24aaf8ecb5c69174383887959ba6f18854d9ae972184680b891af1724b897e1fb8b3af2298c1ccfdc17af504ab8f6b7a9d2f445c7a58dc3b72ef";

//	@Value("${jwt.auth-expiration}")
	private final Long authExpiration = 259200000L; //3 Day

	private final Long refreshExpiration = 2592000000L; //30 Day

	public String extractSubject(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails, Boolean accessToken) {
		if (accessToken) {
			return generateAccessToken(new HashMap<>(), userDetails);
		}
		return generateRefreshToken(new HashMap<>(), userDetails);
	}

	public String generateAccessToken(
		Map<String, Object> extraClaims,
		UserDetails userDetails
	) {
		return Jwts
			.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + authExpiration))
			.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshToken(
		Map<String, Object> extraClaims,
		UserDetails userDetails
	) {
		return Jwts
			.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
			.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractSubject(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}


	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSignKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
