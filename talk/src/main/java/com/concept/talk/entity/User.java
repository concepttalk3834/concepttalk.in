package com.concept.talk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotBlank(message = "Name field is required")
	@Size(min = 2,max = 20,message = "min 2 and max 20 is required")
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@JsonIgnore
	private String password;
	@Column(unique = true)
	private String phoneNumber;
	private Long userrank;
	private Double percentile;
	private String category;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;
	
	@Getter(value = AccessLevel.NONE)
	private boolean enabled = false;
	private String emailToken;
	private String resetToken;
	private LocalDateTime tokenExpiryDate;
	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Providers getProvider() {
		return provider;
	}
	
	public void setProvider(Providers provider) {
		this.provider = provider;
	}
	
	public boolean isEmailVerified() {
		return emailVerified;
	}
	
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	
	public boolean isPhoneVerified() {
		return phoneVerified;
	}
	
	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}
	
	public String getEmailToken() {
		return emailToken;
	}
	
	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(role.name()));
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
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
	
	public String getResetToken() {
		return resetToken;
	}
	
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	public LocalDateTime getTokenExpiryDate() {
		return tokenExpiryDate;
	}
	
	public void setTokenExpiryDate(LocalDateTime tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}
}
