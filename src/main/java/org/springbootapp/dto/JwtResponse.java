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
	private List<String> roles;
	
	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		this.id = id;
		this.accessToken = accessToken;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	
	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}
