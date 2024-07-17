package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.api.movie.elastic.MovieElasticController;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static org.slf4j.MDC.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class MovieElasticIT extends BaseIntegrationTest {

	@Test
	public void getMovieByName() throws Exception {
		this.mockMvc.perform(RestDocumentationRequestBuilders.get(fromMethodCall(on(MovieElasticController.class).search(Pageable.unpaged(), Optional.of("Past"))).build().toUriString()))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isOk());
	}
}
