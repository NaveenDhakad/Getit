package com.getit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expirationMinutes}")
	private long expirationMinutes;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		final Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(Map.of(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		Instant now = Instant.now();
		return Jwts.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
			.signWith(getSignInKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		try {
			byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
			return Keys.hmacShaKeyFor(keyBytes);
		} catch (IllegalArgumentException ex) {
			byte[] raw = jwtSecret.getBytes();
			return Keys.hmacShaKeyFor(raw);
		}
	}
}