package org.springbootapp.service.implement;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Order;
import org.springbootapp.entity.Product;
import org.springbootapp.entity.Role;
import org.springbootapp.entity.User;
import org.springbootapp.repository.IProductRepository;
import org.springbootapp.repository.IUserRepository;
import org.springbootapp.service.IOTPService;
import org.springbootapp.service.IUserService;
import org.springbootapp.utils.TimeUtils;
import org.springbootapp.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IProductRepository productRepository;

	@Autowired
	IOTPService otpService;
	
	@Autowired
	ObjectMapper mapper;


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
	@Transactional
	public void addItemToCart(Long userID, Cart item) {
		Product mergeProduct = productRepository.getOne(item.getProduct().getId());
		Optional<User> findByIdWithItemsGraph = userRepository.findByIdWithItemsGraph(userID);
//		.ifPresent(user -> user.addCartItem(mergeProduct, item.getQty()));
		findByIdWithItemsGraph.ifPresent(user -> user.addCartItem(mergeProduct, item.getQty()));
	}

	@Override
	public List<Cart> getCart(Long userID) {
		Optional<User> findByIdWithItemsGraph = userRepository.findByIdWithItemsGraph(userID);
		return List.copyOf(findByIdWithItemsGraph.get().getItems());
	}
	
	@Override
	@Transactional
	public void updateCartItem(Long userID, Long productID, String action) {
		Product product = productRepository.getOne(productID);
		userRepository.findByIdWithItemsGraph(userID).ifPresent(user -> {
			user.updateCart(product, action);
		});
	}
	
	@Override
	@Transactional
	public void deleteItemIncart(Long userID, Long productID) {
		Product product = productRepository.getOne(productID);
		userRepository.findByIdWithItemsGraph(userID).ifPresent(customer -> customer.removeCartItem(product));
	}
	
	@Override
	public List<Order> getAllOrders(Long userID) {
		Optional<User> findByIdWithOrdersGraph = userRepository.findByIdWithOrdersGraph(userID);
		return List.copyOf(findByIdWithOrdersGraph.get().getOrders());
	}

	@Override
	public boolean registryCutomerAccount(String token, String otp) throws Exception {
		Map<String, Object> information = TokenUtils.getInfomationFromToken(token);
		User user = mapper.convertValue(information.get("user"), User.class);
		String otpCode = (String) information.get("otp");
		Long expiredDate = (Long) information.get("expiredDate");
		if (!TimeUtils.isExpired(new Date(expiredDate)) && otp.equals(otpCode)) {
			userRepository.save(user);
			return true;
		} else {
			throw new RuntimeException(!otp.equals(otpCode) ? "Invalid code!" : "Expired code!");
		}
	}

}