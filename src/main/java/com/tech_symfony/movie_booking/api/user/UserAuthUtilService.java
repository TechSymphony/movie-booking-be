package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.api.bill.Bill;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthUtilService {
	private final UserRepository userRepository;

	public User loadCurrentUser() {
		User user = userRepository.findByEmail(getCurrentUsername())
			.orElseThrow(() -> new UsernameNotFoundException("Conflict"));
		log.info("User: " + user.getId() + " with email " + user.getEmail() + " is authenticated");
		return user;

	}

	public String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	//	public boolean isOwnerBill(Optional<Bill> billOptional) {
//		if (billOptional.isEmpty()) {
//			return false;
//		}
//		return getCurrentUser().getId().equals(billOptional.get().getUser().getId());
//	}
	public boolean isOwner(UUID idObject) {
		User user = loadCurrentUser();
		return user.getId().equals(idObject);
	}
}
