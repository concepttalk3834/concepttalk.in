package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique = true)
	private String email;
	@Column(nullable = true)
	private String password;
	@Column(nullable = false,unique = true)
	private String phoneNumber;
	@Column(nullable = false)
	private Long rank;
	@Column(nullable = false)
	private Double percentile;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.Student;
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
}
