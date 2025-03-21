package com.concept.talk.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class Helper {
	@Value("${BASE_URL}")
	private String BASE_URL;
	
	public static  String getEmailOfLoggedInUser(Authentication authentication){
		if(authentication instanceof OAuth2AuthenticatedPrincipal){
			var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			var oauth2User = (OAuth2User) authentication.getPrincipal();
			String username = "";
			
			if(clientId.equalsIgnoreCase(("google"))) {
				System.out.println("Getting email from google");
				username = oauth2User.getAttribute("email").toString();
			}
			return username;
		}else{
			System.out.println("Getting data from local database");
			return authentication.getName();
		}
	}
	
	public String getLinkForEmailVerification(String emailToken){
		return BASE_URL + "/auth/verify-email?token" + emailToken;
	}
}
