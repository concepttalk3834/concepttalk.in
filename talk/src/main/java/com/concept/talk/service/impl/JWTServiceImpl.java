package com.concept.talk.service.impl;

import com.concept.talk.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	@Override
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		extraClaims.put("roles", roles);
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
//		System.out.println(claims);
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
		Map<String, Object> claims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		System.out.println(roles);
		claims.put("roles", roles);
		return generateToken(claims, userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day expiration
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
//		System.out.println("Extracted Username: " + username);
		
		List<String> rolesFromToken = extractRoles(token);  // Should be plain strings like [ADMIN]
//		System.out.println("Roles from Token: " + rolesFromToken);
		
		List<String> userRoles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
//		System.out.println("User Roles: " + userRoles);
		
		// Check if both sets of roles match
		boolean rolesMatch = new HashSet<>(rolesFromToken).containsAll(userRoles) &&
				new HashSet<>(userRoles).containsAll(rolesFromToken);
//		System.out.println("Roles Match: " + rolesMatch);
		
		// Return true only if the username matches, the token is not expired, and roles match
//		System.out.println(username.equals(userDetails.getUsername()) && !isTokenExpired(token) && rolesMatch);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && rolesMatch;
	}
	
	
	@Override
	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	public List<String> extractRoles(String token){
		Claims claims = extractAllClaims(token);
		return (List<String>) claims.get("roles");
	}
}
