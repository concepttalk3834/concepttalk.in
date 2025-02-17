package com.concept.talk.service.impl;

import com.concept.talk.dto.UserDTO;
import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.OptionalInt;

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRank(userDTO.getRank());
		user.setPercentile(userDTO.getPercentile());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setRole(Role.Student);
		return userRepository.save(user);
	}
	
	@Override
	public Optional<User> authenticateUser(String email, String password) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if(userOptional.isPresent() && passwordEncoder.matches(password,userOptional.get().getPassword())){
			return userOptional;
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
