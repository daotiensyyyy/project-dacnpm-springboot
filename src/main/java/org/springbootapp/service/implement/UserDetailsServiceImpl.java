package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.User;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IOTPService;
import org.springbootapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUserService{
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	public IOTPService otpService;
	
	@Autowired
	User user;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		return user.map(UserDetailsImpl::new).get();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Boolean existsByUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean existsByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			return true;
		} else
			return false;
	}
	
	@Override
	public boolean validateOTP(User user, int otpNum) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		if (otpNum >= 0) {

			int serverOtp = otpService.getOTP(username);
			if (serverOtp > 0) {
				if ((otpNum == serverOtp)) {
					otpService.clearOTP(username);

					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
