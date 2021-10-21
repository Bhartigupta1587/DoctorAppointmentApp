package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.entities.request.LoginRequest;
import com.example.demo.entities.response.LoginResponse;
import com.example.demo.exception.InformationExistException;
import com.example.demo.exception.InformationNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTUtils;
import com.example.demo.security.MyUserDetails;

@RestController
@RequestMapping(path = "auth/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtils jwtUtils;

	public User findUserByUserName(String userName) {

		return userRepository.findByUserName(userName).get();
	}

	// http://localhost:8080/auth/users/register
	@PostMapping("/register")
	public User createUser(@RequestBody User userObject) {
		System.out.println("calling createUser");
		Optional<User> user = userRepository.findByUserName(userObject.getUserName());
		if (user.isPresent()) {
			throw new InformationExistException("User " + userObject.getUserName() + " already exist");
		} else {
			userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
			return userRepository.save(userObject);
		}
	}

	// http://localhost:8080/auth/users/login
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
		System.out.println("calling loginUser");
		if(bindingResult.hasErrors()){
		    bindingResult.getAllErrors().stream().forEach(System.out::println);
		    List<String> returnErrors = new ArrayList<>();
		    for(ObjectError error : bindingResult.getAllErrors()){
		        returnErrors.add(error.getDefaultMessage());
		    }
		    return new ResponseEntity<>(returnErrors, HttpStatus.BAD_REQUEST);
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
			final String JWT = jwtUtils.generateToken(userDetails);
			return ResponseEntity.ok(new LoginResponse(JWT));
		} catch (NullPointerException e) {
			throw new InformationNotFoundException(
					("user with that user name" + loginRequest.getUserName() + "not found"));
		}
	}

	// http://localhost:8080/auth/users/resetPassword
	@PutMapping("/resetPassword")
	public String updatePassword(@RequestBody User userObject) {
		System.out.println("calling Update Password");
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Optional<User> user = userRepository.findByUserName(userDetails.getUsername());
		if (user.isPresent()) {
			user.get().setPassword(passwordEncoder.encode(userObject.getPassword()));
			userRepository.save(user.get());
			return "Password Updated";
		} else {
			throw new InformationNotFoundException("User " + userObject.getUserName() + " did not exist");
		}
	}
}
