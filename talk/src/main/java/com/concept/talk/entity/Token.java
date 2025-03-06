package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tokens")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;
	@Column(nullable = false,unique = true)
	private String token;
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	@Column(nullable = false)
	private boolean expired = false;
	@Column(nullable = false)
    private boolean revoked = false;
	
	public Long getTokenId() {
		return tokenId;
	}
	
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isExpired() {
		return expired;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	public boolean isRevoked() {
		return revoked;
	}
	
	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}
}
