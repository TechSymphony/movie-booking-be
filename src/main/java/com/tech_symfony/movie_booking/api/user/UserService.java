package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.system.exception.UserAlreadyExistsException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenService;

	private final EmailService emailService;

	public void processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {


		User userFromOauth2 = new User();
		userFromOauth2.setFullName(oAuth2User.getAttributes().get("name").toString());
		userFromOauth2.setProvider(Provider.GOOGLE);
		userFromOauth2.setProviderId(oAuth2UserRequest.getClientRegistration().getRegistrationId());
		userFromOauth2.setEmail(oAuth2User.getAttributes().get("email").toString());
		userFromOauth2.setAvatar(oAuth2User.getAttributes().get("picture").toString());
		log.trace("User info is {}", userFromOauth2);

		Optional<User> userOptional = userRepository.findByEmailAndProviderId(userFromOauth2.getEmail(), userFromOauth2.getProviderId());
		User user = userOptional
			.map(existingUser -> updateExistingUser(existingUser, userFromOauth2))
			.orElseGet(() -> registerNewUser(userFromOauth2));
	}

	private User registerNewUser(User user) {

		return userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, User user) {
		existingUser.setFullName(user.getFullName());
		existingUser.setAvatar(user.getAvatar());
		return userRepository.save(existingUser);
	}

	public String register(RegisterRequest registerRequest) {
		if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Email taken");
		}
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setFullName(registerRequest.getFullName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setVerify(false);
		userRepository.save(user);
		String token = tokenService.generateToken(user.getId().toString());
		emailService.sendEmail(
			registerRequest.getEmail(),
			"Verify url",
			"http://localhost:8080/api/v1/auth/verify?t=" + token
		);
		return "Success";
	}

	@Transactional
	public void verifyUser(String token) {
		String userId = tokenService.extractSubject(token);
		User user = userRepository.findByIdAndVerifyFalse(UUID.fromString(userId)).orElseThrow(
			() -> new UsernameNotFoundException("User not found.")
		);
		if(tokenService.isTokenExpired(token)) {
			throw new JwtException("Expired url!");
		}
		user.setVerify(true);
	}
}
