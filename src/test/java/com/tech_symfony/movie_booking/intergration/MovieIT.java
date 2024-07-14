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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Sql("/movie.sql")
public class MovieIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Transactional
	public void getMovies() throws Exception {
		this.mockMvc.perform(get(fromMethodCall(on(MovieController.class).getAllMovies()).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-getAll",
				responseFields(
					subsectionWithPath("_embedded").description("Embedded resources")
				)))
			.andReturn();
	}

	@Test
	@Transactional
	public void getMovieById() throws Exception {
			this.mockMvc.perform(get(fromMethodCall(on(MovieController.class).getMovieById(1)).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-get",
				responseFields(
					fieldWithPath("code").description("The code of the movie"),
					fieldWithPath("name").description("The name of the movie"),
					fieldWithPath("subName").description("The sub name of the movie"),
					fieldWithPath("director").description("The director of the movie"),
					fieldWithPath("caster").description("The caster of the movie"),
					fieldWithPath("releaseDate").description("The release date of the movie"),
					fieldWithPath("endDate").description("The end date of the movie"),
					fieldWithPath("runningTime").description("The running time of the movie"),
					fieldWithPath("language").description("The language of the movie"),
					fieldWithPath("numberOfRatings").description("The number of ratings of the movie"),
					fieldWithPath("sumOfRatings").description("The sum of ratings of the movie"),
					fieldWithPath("description").description("The description of the movie"),
					fieldWithPath("poster").description("The poster of the movie"),
					fieldWithPath("trailer").description("The trailer of the movie"),
					fieldWithPath("rated").description("The rated of the movie"),
					fieldWithPath("slug").description("The slug of the movie"),
					fieldWithPath("horizontalPoster").description("The horizontal poster of the movie"),
					subsectionWithPath("showtimes").description("The showtimes of the movie"),
					subsectionWithPath("genres").description("The genres of the movie"),
					subsectionWithPath("_links").description("Links to other resources")
				)));
	}

	@Test
	@WithMockUser(authorities = {"SAVE_MOVIE"})
	@Transactional
	public void postMovie() throws Exception {
		MovieDTO movieDTO = new MovieDTO();

		movieDTO.setCode("mv-0002");
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
		movieDTO.setHorizontalPoster("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
		movieDTO.setTrailer("https://www.youtube.com/watch?v=sY1S34973zA");
		movieDTO.setRated(5);
		movieDTO.setSlug("the-godfather");

		movieDTO.setGenres(Set.of(1, 2));

		this.mockMvc.perform(
			post(fromMethodCall(on(MovieController.class).create(movieDTO)).build().toUriString())
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(movieDTO)))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isCreated())
			.andDo(document("movies-create",
				requestFields(
					fieldWithPath("code").description("The code of the movie"),
					fieldWithPath("name").description("The name of the movie"),
					fieldWithPath("subName").description("The sub name of the movie"),
					fieldWithPath("director").description("The director of the movie"),
					fieldWithPath("caster").description("The caster of the movie"),
					fieldWithPath("releaseDate").description("The release date of the movie"),
					fieldWithPath("endDate").description("The end date of the movie"),
					fieldWithPath("runningTime").description("The running time of the movie"),
					fieldWithPath("language").description("The language of the movie"),
					fieldWithPath("numberOfRatings").description("The number of ratings of the movie"),
					fieldWithPath("sumOfRatings").description("The sum of ratings of the movie"),
					fieldWithPath("description").description("The description of the movie"),
					fieldWithPath("poster").description("The poster of the movie"),
					fieldWithPath("horizontalPoster").description("The horizontal poster of the movie"),
					fieldWithPath("trailer").description("The trailer of the movie"),
					fieldWithPath("rated").description("The rated of the movie"),
					fieldWithPath("slug").description("The slug of the movie"),
					subsectionWithPath("genres").description("The genres of the movie"),
					fieldWithPath("id").ignored(),
					fieldWithPath("showtimes").ignored()
				)));
	}

	@Test
	@WithMockUser(authorities = {"SAVE_MOVIE"})
	@Transactional
	public void putMovie() throws Exception {
		MovieDTO movieDTO = new MovieDTO();

		movieDTO.setCode("mv-0002");
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

		movieDTO.setGenres(Set.of(1, 2));

		this.mockMvc.perform(
				put(fromMethodCall(on(MovieController.class).update(1, movieDTO)).build().toUriString())
					.contentType(MediaTypes.HAL_JSON)
					.content(objectMapper.writeValueAsString(movieDTO)))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-update",
				requestFields(
					fieldWithPath("code").description("The code of the movie"),
					fieldWithPath("name").description("The name of the movie"),
					fieldWithPath("subName").description("The sub name of the movie"),
					fieldWithPath("director").description("The director of the movie"),
					fieldWithPath("caster").description("The caster of the movie"),
					fieldWithPath("releaseDate").description("The release date of the movie"),
					fieldWithPath("endDate").description("The end date of the movie"),
					fieldWithPath("runningTime").description("The running time of the movie"),
					fieldWithPath("language").description("The language of the movie"),
					fieldWithPath("numberOfRatings").description("The number of ratings of the movie"),
					fieldWithPath("sumOfRatings").description("The sum of ratings of the movie"),
					fieldWithPath("description").description("The description of the movie"),
					fieldWithPath("poster").description("The poster of the movie"),
					fieldWithPath("horizontalPoster").description("The horizontal poster of the movie"),
					fieldWithPath("trailer").description("The trailer of the movie"),
					fieldWithPath("rated").description("The rated of the movie"),
					fieldWithPath("slug").description("The slug of the movie"),
					subsectionWithPath("genres").description("The genres of the movie"),
					fieldWithPath("id").ignored(),
					fieldWithPath("showtimes").ignored()
				)));
	}

	@Test
	@WithMockUser(authorities = {"DELETE_MOVIE"})
	public void deleteMovie() throws Exception {
		this.mockMvc.perform(delete(fromMethodCall(on(MovieController.class).delete(1)).build().toUriString()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isNoContent())
			.andDo(document("movies-delete"));
	}
}
