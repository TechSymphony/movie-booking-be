package com.tech_symfony.movie_booking.api.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserUtils {
	private static UserRepository userRepository;

	public static User getCurrentUser() {
		return userRepository.findByEmail(getCurrentUsername())
			.orElseThrow(() -> new UsernameNotFoundException("Conflict"));

	}

	public static String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
