package com.concept.talk.config;

import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
	@Value("${ADMIN_NAME}")
	private String adminName;
	@Value("${ADMIN_EMAIL}")
    private String adminEmail;
	@Value("${ADMIN_PASSWORD}")
    private String adminPassword;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByEmail(adminEmail).isEmpty()) {
			User admin = new User();
			admin.setName(adminName);
			admin.setEmail(adminEmail);
			admin.setPassword(passwordEncoder.encode(adminPassword));
			admin.setRole(Role.ADMIN);
			admin.setEmailVerified(true);
			admin.setPhoneVerified(true);
			admin.setEnabled(true);
			userRepository.save(admin);
			System.out.println("Default admin user created.");
		}
	}
}
