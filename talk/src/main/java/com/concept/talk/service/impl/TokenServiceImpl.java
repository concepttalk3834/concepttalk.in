package com.concept.talk.service.impl;

import com.concept.talk.entity.Token;
import com.concept.talk.entity.User;
import com.concept.talk.repository.TokenRepository;
import com.concept.talk.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
	private final TokenRepository tokenRepository;
	
	public TokenServiceImpl(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}
	
	@Override
	public void saveToken(Token token) {
		tokenRepository.save(token);
	}
	
	@Override
	public void revokeAllUserTokens(User user) {
		List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
		if(validTokens.isEmpty()) return;
		
		validTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validTokens);
	}
	
	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}
}
