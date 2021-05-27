package org.springbootapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenValidationCodeRequestWrapper {
	private int otp;
	private String token;
}