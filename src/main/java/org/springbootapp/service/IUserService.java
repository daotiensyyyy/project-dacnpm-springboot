package org.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.User;
import org.springframework.security.core.Authentication;

public interface IUserService {
	
	User save(User user);
	
	List<User> getAll();
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	boolean validateOTP(User user, int otpNum);
	
	Optional<User> findUserByEmail(String email);
	
	Optional<User> findUserById(Long id);
	
	Optional<User> findUserByResetToken(String resetToken);
	
	void delete(Long id);
	
	Optional<User> checkActiveAccount(String username);
	
	User getCurrentlyLoggedInUser(Authentication auth);
	
	User findUserByUsername(String username);

	void addItemToCart(Long customerID, Cart item);
	
	List<Cart> getCart(Long userID);
	
	
}
