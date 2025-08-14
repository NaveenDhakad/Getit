package com.getit.service;

import com.getit.model.Role;
import com.getit.model.User;
import com.getit.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User registerUser(String email, String rawPassword, String fullName) {
		if (userRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Email already in use");
		}
		User user = User.builder()
			.email(email)
			.passwordHash(passwordEncoder.encode(rawPassword))
			.fullName(fullName)
			.roles(Set.of(Role.ROLE_USER))
			.enabled(true)
			.build();
		return userRepository.save(user);
	}

	public User getCurrentUserOrThrow() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepository.findByEmail(email).orElseThrow();
	}
}