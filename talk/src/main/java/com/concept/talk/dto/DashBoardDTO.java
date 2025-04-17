package com.concept.talk.dto;

public class DashBoardDTO {
	private String name;
	private String email;
	private String phoneNumber;
	private Long userRank;
	private Double percentile;
	private String category;
	private boolean phoneVerified;
	private boolean emailVerified;
	
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Long getUserRank() {
		return userRank;
	}
	
	public void setUserRank(Long userRank) {
		this.userRank = userRank;
	}
	
	public Double getPercentile() {
		return percentile;
	}
	
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isPhoneVerified() {
		return phoneVerified;
	}
	
	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}
	
	public boolean isEmailVerified() {
		return emailVerified;
	}
	
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
}
