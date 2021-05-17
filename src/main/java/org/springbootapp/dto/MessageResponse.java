package org.springbootapp.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
	private String message;
	private List<String> errors;

	public MessageResponse(String message, String error) {
		super();
		this.message = message;
		errors = Arrays.asList(error);
	}
	
	public MessageResponse(String message) {
		super();
		this.message = message;
	}

}