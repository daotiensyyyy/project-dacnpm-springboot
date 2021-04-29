package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.User;

public interface IUserService {
	
	User save(User user);
	
	List<User> getAll();
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	boolean validateOTP(User user, int otpNum);
	
	Optional<User> findUserByEmail(String email);
	
	Optional<User> findUserByResetToken(String resetToken);
	
}
