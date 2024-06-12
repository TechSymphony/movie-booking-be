package com.tech_symfony.movie_booking.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

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
}
