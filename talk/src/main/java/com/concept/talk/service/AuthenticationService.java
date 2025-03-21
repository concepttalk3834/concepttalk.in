package com.concept.talk.service;

import com.concept.talk.dto.JWTAuthenticationResponse;
import com.concept.talk.dto.RefreshTokenRequest;
import com.concept.talk.dto.SignInRequest;
import com.concept.talk.dto.SignUpRequest;
import com.concept.talk.entity.User;

import java.util.List;
import java.util.Optional;


public interface AuthenticationService {
	User registerUser(SignUpRequest signUpRequest);
	JWTAuthenticationResponse authenticateUser(SignInRequest signInRequest);
	Optional<User> findByEmail(String email);
	JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
	List<User> getAllUsers();
}
