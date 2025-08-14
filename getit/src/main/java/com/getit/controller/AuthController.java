package com.getit.controller;

import com.getit.dto.RegisterRequest;
import com.getit.model.User;
import com.getit.service.UserService;
import com.getit.util.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		User user = userService.registerUser(request.getEmail(), request.getPassword(), request.getFullName());
		UserDetails userDetails = org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password(user.getPasswordHash())
			.authorities("ROLE_USER")
			.build();
		String token = jwtService.generateToken(userDetails);
		return ResponseEntity.ok(Map.of("token", token, "email", user.getEmail(), "fullName", user.getFullName()));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
		String email = payload.get("email");
		String password = payload.get("password");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(userDetails);
		return ResponseEntity.ok(Map.of("token", token));
	}
}