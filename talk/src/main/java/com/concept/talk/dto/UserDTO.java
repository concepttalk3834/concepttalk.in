package com.concept.talk.dto;

import com.concept.talk.entity.Role;
import com.concept.talk.entity.User;
import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private Long rank;
	private Double percentile;
}
