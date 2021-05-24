package org.springbootapp.service.implement;

import java.util.List;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.User;
import org.springbootapp.repository.IProductRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IProductRepository productRepository;

	@Autowired
	public IOTPService otpService;

	@Autowired
	User user;

	@Autowired
	Product product;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		return user.map(UserDetailsImpl::new).get();
	}

	@Override
	public User save(User user) {
//		user.setRole("ROLE_USER");	///////////////////////////////////
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

	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	@Override
	public void delete(Long id) {
		Optional<User> user_deleted = userRepository.findById(id);
		if (user_deleted.isPresent()) {
			User userInActive = user_deleted.get();
			userInActive.setActive(false);
			userRepository.save(userInActive);
		}
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return Optional.of(userRepository.findById(id).get());
	}

	@Override
	public Optional<User> checkActiveAccount(String username) {
		return userRepository.findActiveAccount(username);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public User getCurrentlyLoggedInUser(Authentication auth) {
		if (auth == null)
			return null;
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			user = findUserByUsername(username);
		} else {
			String username = principal.toString();
			user = findUserByUsername(username);
		}

//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		String username = userDetails.getUsername();
//		user = findUserByUsername(username);
		return user;
	}

	@Override
	@Transactional
	public List<Cart> addItemToCart(Long userID, Cart item) {
		Product mergeProduct = productRepository.getOne(item.getProduct().getId());
		Optional<User> findByIdWithItemsGraph = userRepository.findByIdWithItemsGraph(userID);
//		.ifPresent(user -> user.addCartItem(mergeProduct, item.getQty()));
		findByIdWithItemsGraph.ifPresent(user -> user.addCartItem(mergeProduct, item.getQty()));
		return List.copyOf(findByIdWithItemsGraph.get().getItems());
	}

	@Override
	public List<Cart> getCart(Long userID) {
		Optional<User> findByIdWithItemsGraph = userRepository.findByIdWithItemsGraph(userID);
		return List.copyOf(findByIdWithItemsGraph.get().getItems());
	}

}
