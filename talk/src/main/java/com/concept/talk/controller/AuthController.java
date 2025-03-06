package com.concept.talk.controller;

import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.UserService;
import com.concept.talk.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final UserServiceImpl userService;
	
	public AuthController(UserServiceImpl userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User registered successfully";
	}

	@PostMapping("/login")
	public String login() {
		// Authentication is handled by Spring Security
		return "User logged in successfully";
	}
}