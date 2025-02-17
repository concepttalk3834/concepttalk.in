package com.concept.talk.controller;

import com.concept.talk.dto.UserDTO;
import com.concept.talk.entity.User;
import com.concept.talk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO){
		User user = userService.registerUser(userDTO);
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password){
		Optional<User> userOptional = userService.authenticateUser(email, password);
		return userOptional.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.status(401).build());
	}
}
