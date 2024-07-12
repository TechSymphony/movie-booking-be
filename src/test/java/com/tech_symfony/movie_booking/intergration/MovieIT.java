package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.movie.*;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.jdbc.Sql;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Sql("/movie.sql")
public class MovieIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getMovies() throws Exception {
		this.mockMvc.perform(get(fromMethodCall(on(MovieController.class).getAllMovies()).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-getAll"))
			.andReturn();
	}

	@Test
	public void getMovieById() throws Exception {
			this.mockMvc.perform(get(fromMethodCall(on(MovieController.class).getMovieById(1)).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-get"));
	}

	@Test
	public void saveMovie() throws Exception {
		MovieDTO movieDTO = new MovieDTO();

		movieDTO.setName("The Godfather");
		movieDTO.setSubName("The Godfather");
		movieDTO.setDirector("Francis Ford Coppola");
		movieDTO.setCaster("Mario Puzo");
		movieDTO.setReleaseDate("1972-03-24");
		movieDTO.setEndDate("1972-03-24");
		movieDTO.setRunningTime(175);
		movieDTO.setLanguage("English");
		movieDTO.setNumberOfRatings(100);
		movieDTO.setSumOfRatings(1000);
		movieDTO.setDescription("The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.");
		movieDTO.setPoster("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
		movieDTO.setTrailer("https://www.youtube.com/watch?v=sY1S34973zA");
		movieDTO.setRated(5);
		movieDTO.setSlug("the-godfather");

		movieDTO.setShowtimes(Set.of(1, 2));
		movieDTO.setGenres(Set.of(1, 2));

		System.out.println(objectMapper.writeValueAsString(movieDTO));
		this.mockMvc.perform(
			post(fromMethodCall(on(MovieController.class).create(movieDTO)).build().toUriString())
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(movieDTO)))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isCreated())
			.andDo(document("movies-create"));
	}

	@Test
	public void deleteMovie() throws Exception {
		this.mockMvc.perform(delete(fromMethodCall(on(MovieController.class).delete(1)).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-delete"));
	}
}
