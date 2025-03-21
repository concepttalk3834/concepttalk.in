package com.concept.talk.controller;

import com.concept.talk.dto.DashBoardDTO;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class DashBoardController {
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger LOGGER = Logger.getLogger(DashBoardController.class.getName());
	
	@GetMapping("/profile")
	public ResponseEntity<?> dashboard(Authentication authentication){
		String email = Helper.getEmailOfLoggedInUser(authentication);
		Optional<User> user = userRepository.findByEmail(email);
		
		if (!user.isPresent()) {
			LOGGER.warning("User not found with email: " + email);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		LOGGER.info("User fetched with email: " + email);
		return ResponseEntity.ok(user.get());
	}
	
	@PostMapping("/profile/update")
	public ResponseEntity<String> updateProfile(Authentication authentication, @RequestBody DashBoardDTO dashBoardDTO){
		if (authentication == null || !authentication.isAuthenticated()) {
			LOGGER.warning("Unauthenticated user trying to access update profile");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
		}
		
		String email = Helper.getEmailOfLoggedInUser(authentication);
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (!optionalUser.isPresent()) {
			LOGGER.warning("User not found with email: " + email);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		
		LOGGER.info("Updating profile for user with email: " + email);
		
		User user = optionalUser.get();
		updateUserDetails(user, dashBoardDTO);
		userRepository.save(user);
		
		return ResponseEntity.ok("Profile updated successfully!");
	}
	
	private void updateUserDetails(User user, DashBoardDTO dashBoardDTO) {
		user.setName(dashBoardDTO.getName());
		user.setPhoneNumber(dashBoardDTO.getPhoneNumber());
		user.setUserrank(dashBoardDTO.getUserRank());
		user.setPercentile(dashBoardDTO.getPercentile());
		user.setCategory(dashBoardDTO.getCategory());
	}
}
