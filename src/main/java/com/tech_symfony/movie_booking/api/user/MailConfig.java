package com.tech_symfony.movie_booking.api.user;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MailConfig {

	@Value("${spring.security.oauth2.client.registration.google.clientId:default}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.clientSecret:default}")
	private String clientSecret;

	@Value("${mail.username:default}")
	private String fromEmail;

	public void accessToken(JavaMailSenderImpl mailSender) throws IOException {
		GoogleCredential credential = credential();
		if (credential.getAccessToken() == null || credential.getExpiresInSeconds() != null && credential.getExpiresInSeconds() <= 60) {
			credential.refreshToken();
		}
		String accessToken = credential.getAccessToken();
		mailSender.setPassword(accessToken);
	}

	@Bean
	@Lazy
	public JavaMailSender getJavaMailSender() throws IOException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername(fromEmail);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth.mechanisms", "XOAUTH2");

		accessToken(mailSender);

		return mailSender;
	}

	@Bean
	public GoogleCredential credential() {
		return new GoogleCredential.Builder()
			.setClientSecrets(clientId, clientSecret)
			.setTransport(new NetHttpTransport())
			.setJsonFactory(new JacksonFactory())
			.build();
	}
}

