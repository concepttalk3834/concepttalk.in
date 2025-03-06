package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = true)
	private String password;
	@Column(nullable = false)
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
	@Column(name = "modified_at", nullable = false, updatable = false)
	private LocalDateTime modifiedAt = LocalDateTime.now();
}
