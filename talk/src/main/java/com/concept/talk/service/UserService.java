package com.concept.talk.service;

import com.concept.talk.dto.UserDTO;
import com.concept.talk.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
	User registerUser(UserDTO userDTO);
	Optional<User> authenticateUser(String email, String password);
	Optional<User> findByEmail(String email);
}
