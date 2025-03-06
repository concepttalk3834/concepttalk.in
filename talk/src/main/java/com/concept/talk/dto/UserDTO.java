package com.concept.talk.dto;

import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import lombok.Data;

public class UserDTO {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private Long userRank;
	private Double percentile;
	
	
	public Long getUserRank() {
		return userRank;
	}
	
	public void setUserRank(Long userRank) {
		this.userRank = userRank;
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
	
	public Double getPercentile() {
		return percentile;
	}
	
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
}
