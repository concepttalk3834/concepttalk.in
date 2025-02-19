package com.concept.talk.service;

import com.concept.talk.dto.UserDTO;
import com.concept.talk.entity.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public interface UserService {
	User registerUser(UserDTO userDTO);
	Optional<User> authenticateUser(String email, String password);
	Optional<User> findByEmail(String email);
}
