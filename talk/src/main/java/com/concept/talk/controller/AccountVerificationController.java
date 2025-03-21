package com.concept.talk.controller;

import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.util.Message;
import com.concept.talk.util.MessageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountVerificationController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token") String token){
		User user = userRepository.findByEmailToken(token).orElse(null);
		
		if(user != null){
			if(user.getEmailToken().equals(token)){
				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepository.save(user);
				return "success";
			}
		}
		return "error_page";
	}
}
