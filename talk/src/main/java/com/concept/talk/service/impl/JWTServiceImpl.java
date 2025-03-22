package com.concept.talk.service.impl;

import com.concept.talk.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	@Override
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		extraClaims.put("role", userDetails.getAuthorities());
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 604800000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	@Override
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Key getSignInKey(){
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
			Map<String, Objects> extraClaims,
			UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	@Override
	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
