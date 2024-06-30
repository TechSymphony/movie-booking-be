package com.tech_symfony.movie_booking.api.user.auth;

import com.tech_symfony.movie_booking.api.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
	private final UserService userService;


	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		log.trace("Load user {}", oAuth2UserRequest);
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
		userService.processOAuth2User(oAuth2UserRequest, oAuth2User);
		return oAuth2User;
	}


}
