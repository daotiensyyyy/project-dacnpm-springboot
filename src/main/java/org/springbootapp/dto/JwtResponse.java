package org.springbootapp.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
	private Long id;
	private String accessToken;
	private String type = "Bearer";
	private String username;
	private String email;
	private String address;
	private String phone;
	private String role;
	
	public JwtResponse(String accessToken, Long id, String username, String email, String address, String phone, String role) {
		this.id = id;
		this.accessToken = accessToken;
		this.username = username;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.role = role;
	}
	
	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}
