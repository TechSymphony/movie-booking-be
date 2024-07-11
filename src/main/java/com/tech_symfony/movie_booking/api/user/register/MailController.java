package com.tech_symfony.movie_booking.api.user.register;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Class này dùng để lấy refresh token từ gg để dùng Gmail API
//Trước tiên phải enable Gmail API trong gg dev console, email dùng để gửi thư phải là account của gg dev console đó
//gọi /api/v1/auth/mail trong trình duyệt để tiến hành đăng nhập và app sẽ lấy refresh token để dùng
//chi tiết config trong class MailConfig
//lưu ý redirect uri phải được đăng ký đúng (trong trường hợp đang sử dụng là http://localhost:8080/api/v1/auth/callback)
@BasePathAwareController
@RequiredArgsConstructor
public class MailController {

	@Value("${spring.security.oauth2.client.registration.google.clientId:default}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.clientSecret:default}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri[3]:default}")
	private String redirectUri;

	private final GoogleCredential credential;

	@GetMapping("/auth/mail")
	public String authorize() {
		AuthorizationCodeRequestUrl authorizationUrl = new AuthorizationCodeRequestUrl(
			"https://accounts.google.com/o/oauth2/auth", clientId)
			.setRedirectUri(redirectUri)
			.setScopes(Collections.singletonList("https://mail.google.com/"))
			.setResponseTypes(Collections.singletonList("code"))
			.set("access_type", "offline")  // Để yêu cầu refresh token
			.set("prompt", "consent"); //
		return "redirect:" + authorizationUrl;
	}

	@GetMapping("api/v1/auth/callback")
	public String callback(@RequestParam("code") String authorizationCode) {
		String exchangeTokenUrl = linkTo(
			methodOn(MailController.class).exchangeToken(authorizationCode)
		).toUri().toString();
		return "redirect:" + exchangeTokenUrl;
	}

	@GetMapping("/auth/exchange-token")
	public ResponseEntity<String> exchangeToken(@RequestParam("code") String authorizationCode) {
		try {
			GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
				new NetHttpTransport(),
				new JacksonFactory(),
				"https://oauth2.googleapis.com/token",
				clientId,
				clientSecret,
				authorizationCode,
				redirectUri)
				.execute();
			String refreshToken = response.getRefreshToken();
			credential.setRefreshToken(refreshToken);
			return ResponseEntity.ok("Tokens retrieved successfully");
		} catch (IOException e) {
			return ResponseEntity.status(400).body("Error retrieving tokens");
		}
	}
}
