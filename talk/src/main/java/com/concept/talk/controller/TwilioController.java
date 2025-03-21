package com.concept.talk.controller;

import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TwilioController {
	@Value("${twilio.account_sid}")
	private String accountSid;
	@Value("${twilio.auth_token}")
	private String authToken;
	@Value("${twilio.service_sid}")
	private String twilioServiceId;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() {
		Twilio.init(accountSid, authToken);
	}
	
	@PostMapping("/sendOtp")
	public String sendVerificationCode(@RequestParam String phoneNumber) {
		String formattedPhoneNumber = "+91" + phoneNumber;
		
		Verification verification = Verification.creator(twilioServiceId, formattedPhoneNumber, "sms").create();
		return verification.getStatus();
		
	}
	
	@PostMapping("/validateOtp")
	public String validateVerificationCode(@RequestParam String phoneNumber, @RequestParam String verificationCode) {
		String formattedPhoneNumber = "+91" + phoneNumber;
		VerificationCheck verificationCheck = VerificationCheck.creator(
						twilioServiceId)
				.setTo(formattedPhoneNumber)
				.setCode(verificationCode)
				.create();
		
		String verificationStatus = verificationCheck.getStatus();
		
		if("approved".equals(verificationStatus)){
			Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
			if(optionalUser.isPresent()){
				User user = optionalUser.get();
				user.setPhoneVerified(true);
				user.setEnabled(true);
				userRepository.save(user);
			}
		}
		
		return verificationStatus;
	}
}
