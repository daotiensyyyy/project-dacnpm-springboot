package org.springbootapp.api;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springbootapp.dto.JwtResponse;
import org.springbootapp.dto.LoginRequest;
import org.springbootapp.dto.MessageResponse;
import org.springbootapp.dto.SignupRequest;
import org.springbootapp.entity.User;
import org.springbootapp.jwt.JwtUtils;
import org.springbootapp.service.IEmailService;
import org.springbootapp.service.IOTPService;
import org.springbootapp.service.IRoleService;
import org.springbootapp.service.IUserService;
import org.springbootapp.service.implement.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public IOTPService otpService;

	@Autowired
	public IEmailService emailService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		String roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
		return ResponseEntity
				.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getPassword()));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest,
			HttpServletRequest request) throws MessagingException {
		if (userService.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Username is already taken!"));
		}
		if (userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERR: Email is already in use!"));
		}

		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(), signupRequest.getPhone(),
				signupRequest.getRole(), signupRequest.isActive());

//		String role = signupRequest.getRole();
//
//		user.setRole(role);
//		userService.save(user);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		int otp = otpService.generateOTP(username);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("sydao1579@gmail.com");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setText(" Hi " + user.getUsername() + "!"
				+ "\n Welcome to BHX! Please enter your OTP code to complete registration." + "\n Your OTP number is : "
				+ otp);

		emailService.sendEmail(mailMessage);

		return new ResponseEntity<>(otp, HttpStatus.OK);
	}

	@RequestMapping(value = "/register/validate", method = RequestMethod.POST)
	public ResponseEntity<?> validateOTP(@RequestParam("otpnum") int otpNum, @RequestBody SignupRequest signupRequest) {
		String msg = "Your account is now actived !";
		try {
			User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
					encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(), signupRequest.getPhone(),
					signupRequest.getRole(), signupRequest.isActive());

			userService.validateOTP(user, otpNum);
			String role = user.getRole();

			user.setRole(role);
			userService.save(user);

			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userService.getAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

}
