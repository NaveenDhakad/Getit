package com.getit.config;

import com.getit.model.Role;
import com.getit.model.User;
import com.getit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Bean
	CommandLineRunner initAdmin() {
		return args -> {
			if (!userRepository.existsByEmail("admin@getit.app")) {
				User admin = User.builder()
					.email("admin@getit.app")
					.passwordHash(passwordEncoder.encode("Admin@123"))
					.fullName("GETIT Admin")
					.roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
					.enabled(true)
					.city("Indore")
					.country("IN")
					.latitude(22.7196)
					.longitude(75.8577)
					.build();
				userRepository.save(admin);
			}
		};
	}
}