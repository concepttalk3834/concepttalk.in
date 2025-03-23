package com.concept.talk.service.impl;

import com.concept.talk.dto.JWTAuthenticationResponse;
import com.concept.talk.dto.RefreshTokenRequest;
import com.concept.talk.dto.SignInRequest;
import com.concept.talk.dto.SignUpRequest;
import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.AuthenticationService;
import com.concept.talk.service.JWTService;
import com.concept.talk.util.EmailService;
import com.concept.talk.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final Helper helper;
	@Autowired
	private EmailService emailService;
	
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, Helper helper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.helper = helper;
	}
	
	@Override
	public User registerUser(SignUpRequest signUpRequest) {
		if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exists");
		}
		
		User user = new User();
		
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		user.setUserrank(signUpRequest.getUserRank());
		user.setPercentile(signUpRequest.getPercentile());
		user.setCategory(signUpRequest.getCategory());
		user.setRole(Role.USER);
		String emailToken = UUID.randomUUID().toString();
		user.setEmailToken(emailToken);
		User savedUser = userRepository.save(user);
		String emailLink = helper.getLinkForEmailVerification(emailToken);
		emailService.sendVerificationEmail(savedUser.getEmail(),"Verify Account: Concept Talk JEE",emailLink);
		return savedUser;
	}
	
	@Override
	public JWTAuthenticationResponse authenticateUser(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
		
		var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email and Password"));
		
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
		
		JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
		
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		jwtAuthenticationResponse.setEmail(user.getEmail());
		jwtAuthenticationResponse.setName(user.getName());
		
		return jwtAuthenticationResponse;
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
			var jwt = jwtService.generateToken(user);
			
			JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
			
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			jwtAuthenticationResponse.setEmail(user.getEmail());
			jwtAuthenticationResponse.setName(user.getName());
			
			return jwtAuthenticationResponse;
		}
		return null;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public void logout(String authorizationHeader) {
		String token = authorizationHeader.replace("Bearer ", "");
	}
}
