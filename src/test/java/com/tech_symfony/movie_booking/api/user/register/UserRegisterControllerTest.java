package com.tech_symfony.movie_booking.api.user.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.api.user.UserRepository;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import com.tech_symfony.movie_booking.system.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserRegisterControllerTest extends BaseIntegrationTest {

	@Autowired
	TokenService tokenService;

	@Autowired
	UserRepository userRepository;

	@MockBean
	EmailService emailService;

	@BeforeEach
	public void setup() {
		doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
	}


	@Test
	@WithMockUser(username = "testuser", authorities = {"SAVE_USER"})
	void register() throws Exception {
		RegisterRequest data = RegisterRequest.builder()
			.email("nngocsang445@gmail.com")
			.password("sang123456789")
			.fullName("Sangggg")
			.build();
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(data);
		mockMvc.perform(
			post(
				MvcUriComponentsBuilder
					.fromMethodName(UserRegisterController.class, "register", data)
					.build()
					.toUri())
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(content().string("\"Success\""));
	}

	@Test
	void verify() throws Exception {
		User user = userRepository.findByEmail("admin@gmail.com").orElse(null);
		if (user!=null) {
			String token = tokenService.generateToken(user.getId().toString());
			mockMvc.perform(
					get(
						MvcUriComponentsBuilder
							.fromMethodName(UserRegisterController.class, "verify", token)
							.build()
							.toUri())
						.param("t", token))
				.andExpect(status().isOk())
				.andExpect(content().string("\"Success\""));
		}

	}

	@Test
	void unverified() throws Exception {
		mockMvc.perform(
				get(
					MvcUriComponentsBuilder
						.fromMethodName(UserRegisterController.class, "unverified")
						.build()
						.toUri()))
			.andExpect(status().isFound());
	}
}
