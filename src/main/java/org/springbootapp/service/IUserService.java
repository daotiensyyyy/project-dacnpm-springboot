package org.springbootapp.service;

import java.util.List;

import org.springbootapp.entity.User;

public interface IUserService {
	
	User save(User user);
	
	List<User> getAll();
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	boolean validateOTP(User user, int otpNum);
	
}
