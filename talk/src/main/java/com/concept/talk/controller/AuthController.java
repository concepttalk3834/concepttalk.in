package com.concept.talk.controller;

import com.concept.talk.dto.*;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.impl.AuthenticationServiceImpl;
import com.concept.talk.util.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationServiceImpl authenticationService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthController(AuthenticationServiceImpl authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.registerUser(signUpRequest));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
		return ResponseEntity.ok(authenticationService.authenticateUser(signInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader(value = "Authorization") String authorizationHeader) {
		// Invalidate the refresh token on the server-side if required
		authenticationService.logout(authorizationHeader);
		return ResponseEntity.ok("Successfully logged out");
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			String resetToken = UUID.randomUUID().toString();
			user.setResetToken(resetToken);
			user.setTokenExpiryDate(LocalDateTime.now().plusHours(1));
			userRepository.save(user);
			
			// Send the reset link via email
			try {
				emailService.sendResetTokenLink(email, resetToken);
				return ResponseEntity.ok("Password reset link sent successfully.");
			} catch (MessagingException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Failed to send password reset email.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
		}
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
		Optional<User> optionalUser = userRepository.findByResetToken(request.getToken());
		
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			// Check if the token has expired
			if (user.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reset token has expired.");
			}
			
			// Update the user's password
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			user.setResetToken(null);
			user.setTokenExpiryDate(null);
			userRepository.save(user);
			
			return ResponseEntity.ok("Password has been reset successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reset token.");
		}
	}
}
