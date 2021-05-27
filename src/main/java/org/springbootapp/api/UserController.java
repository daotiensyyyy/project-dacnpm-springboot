package org.springbootapp.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springbootapp.dto.JwtResponse;
import org.springbootapp.dto.LoginRequest;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.dto.SignupRequest;
import org.springbootapp.dto.TokenResponseWrapper;
import org.springbootapp.dto.TokenValidationCodeRequestWrapper;
import org.springbootapp.entity.Order;
import org.springbootapp.entity.User;
import org.springbootapp.jwt.JwtUtils;
import org.springbootapp.service.IEmailService;
import org.springbootapp.service.IOTPService;
import org.springbootapp.service.IRoleService;
import org.springbootapp.service.IUserService;
import org.springbootapp.service.implement.UserDetailsImpl;
import org.springbootapp.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IUserService userService;

	@Autowired
	IRoleService roleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	IOTPService otpService;

	@Autowired
	IEmailService emailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	// login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

		if (userService.checkActiveAccount(loginRequest.getUsername()).isPresent()) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), userDetails.getAddress(), userDetails.getPhone(), roles.get(0)));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("ERR: Your account doesn't exist"));
	}

	// register
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest,
			HttpServletRequest request) throws MessagingException, JsonProcessingException {
		if (userService.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Username is already taken!"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Email is already in use!"));
		}

		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(), signupRequest.getPhone(),
				signupRequest.getRole(), signupRequest.isActive());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		String otp = String.valueOf(otpService.generateOTP(username));

		Map<String, Object> information = new HashMap<>();
		information.put("user", user);
		information.put("otp", otp);
		String token = TokenUtils.generateToken(information);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("sydao1579@gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setText(" Hi " + user.getUsername() + "!"
				+ "\n Welcome to BHX! Please enter your OTP code to complete registration." + "\n Your OTP number is : "
				+ otp);

		emailService.sendEmail(mailMessage);

		return new ResponseEntity<>(new TokenResponseWrapper(token), HttpStatus.OK);
	}

	// validate otp
	@RequestMapping(value = "/register/validate", method = RequestMethod.POST)
	public ResponseEntity<?> validateOTP(@RequestBody TokenValidationCodeRequestWrapper request) throws Exception {
		String otp = String.valueOf(request.getOtp());
		boolean registryCutomerAccount = userService.registryCutomerAccount(request.getToken(), otp);
		if (registryCutomerAccount) {
			return ResponseEntity.ok().body(new MessageResponse("Your account is activated !"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Wrong OTP !"));
		}
	}

	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	public ResponseEntity<?> processForgetPasswordForm(@RequestBody Map<String, String> requestPa,
			HttpServletRequest request) {

		Optional<User> optional = userService.findUserByEmail(requestPa.get("email"));

		if (!optional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			User user = optional.get();
			user.setResetToken(UUID.randomUUID().toString());
			userService.save(user);
			String appUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("sydao1579@gmail.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
					+ user.getResetToken());
			emailService.sendEmail(passwordResetEmail);
			return new ResponseEntity<>(user.getResetToken(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity<?> setNewPassword(@RequestBody Map<String, String> requestParams, RedirectAttributes redir) {
		Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));
		if (user.isPresent()) {
			User resetUser = user.get();
			resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
			resetUser.setResetToken(null);
			userService.save(resetUser);
			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// get all users
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// delete user
	@RequestMapping(value = "/users/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			userService.delete(id);
			return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/user/{cid}/orders", method = RequestMethod.GET)
	public ResponseEntity<?> getAllOrders(@PathVariable("cid") Long userID) {
		List<Order> obj = userService.getAllOrders(userID);
		return ResponseEntity.ok(obj);
	}

}
