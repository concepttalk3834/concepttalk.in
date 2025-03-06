package com.concept.talk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique = true)
	private String email;
	@Column(nullable = true)
	private String password;
	@Column(nullable = false,unique = true)
	private String phoneNumber;
	@Column(nullable = false,name = "user_rank")
	private Long userrank;
	@Column(nullable = false)
	private Double percentile;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.Student;
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Long getId() {
		return Id;
	}
	
	public void setId(Long Id) {
		this.Id = Id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Long getUserrank() {
		return userrank;
	}
	
	public void setUserrank(Long userrank) {
		this.userrank = userrank;
	}
	
	public Double getPercentile() {
		return percentile;
	}
	
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
