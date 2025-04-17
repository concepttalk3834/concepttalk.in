package com.concept.talk.config;

import com.concept.talk.entity.Providers;
import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import com.concept.talk.repository.UserRepository;
import com.concept.talk.service.impl.JWTServiceImpl;
import com.concept.talk.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	Logger log = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTServiceImpl jwtService;
	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("OAuthAuthenticationSuccessHandler");
		
		var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
		log.info(authorizedClientRegistrationId);
		
		DefaultOAuth2User oUser = (DefaultOAuth2User) authentication.getPrincipal();
		
		String email = oUser.getAttribute("email").toString();
		String name = oUser.getAttribute("name").toString();
		
		User user = userRepository.findByEmail(email).orElse(null);
		
		if (user == null) {
			user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setProvider(Providers.GOOGLE);
			user.setEnabled(true);
			user.setEmailVerified(true);
			user.setPercentile(20.00);
			user.setPhoneNumber("123456789");
			user.setRole(Role.USER);
			user.setUserrank(3654L);
			user.setCategory("");
			
			userRepository.save(user);
			log.info("User saved successfully " + email);
		}
		
		// ✅ Load user details properly
		UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
		
		// ✅ Generate token using loaded user details
		String token = jwtService.generateToken(userDetails);
		
		String redirectUrl = "http://localhost:5173/oauth-success?token=" + token;
		
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
