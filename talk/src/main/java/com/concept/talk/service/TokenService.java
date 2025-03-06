package com.concept.talk.service;

import com.concept.talk.entity.Token;
import com.concept.talk.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TokenService {
	void saveToken(Token token);
	void revokeAllUserTokens(User user);
	Optional<Token> findByToken(String token);
}
