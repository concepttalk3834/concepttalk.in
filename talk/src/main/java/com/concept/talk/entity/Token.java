package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tokens")
@Data
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
}
