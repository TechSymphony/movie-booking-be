package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.MovieBookingApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieBookingApplication.class)
@AutoConfigureMockMvc
public class MailControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAuthorize() throws Exception {
		mockMvc.perform(get("/auth/mail"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("https://accounts.google.com/o/oauth2/auth*"));
	}

	@Test
	public void testCallback() throws Exception {
		String authorizationCode = "test-code";
		mockMvc.perform(get("/auth/api/v1/auth/callback").param("code", authorizationCode))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("/api/v1/auth/exchangeToken*"));
	}
}
