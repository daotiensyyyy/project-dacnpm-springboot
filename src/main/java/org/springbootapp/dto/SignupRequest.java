package org.springbootapp.dto;

import org.springbootapp.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	private String username;
	private String email;
	private String password;
	private String address;
	private String phone;
	private String role;
	private boolean active;

}
