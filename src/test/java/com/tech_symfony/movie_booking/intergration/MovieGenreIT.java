package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieGenreIT extends BaseIntegrationTest {

	@Autowired
	EntityLinks entityLinks;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Order(0)
	@WithMockUser(authorities = {"SAVE_MOVIE_GENRE"})
	public void postMovieGenre() throws Exception {

		MovieGenre movieGenre = new MovieGenre();
		movieGenre.setName("Action");

		this.mockMvc.perform(post(entityLinks.linkToCollectionResource(MovieGenre.class).toUri())
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(movieGenre)))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isCreated())
		.andDo(document("movieGenre-post", requestFields(
			fieldWithPath("id").ignored(),
			fieldWithPath("name").description("The name of the movie genre")
		)))
		.andReturn();
	}

	@Test
	@Order(1)
	public void getAllMovieGenre() throws Exception {
		this.mockMvc.perform(get(entityLinks.linkToCollectionResource(MovieGenre.class).toUri()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movieGenre-get",
				responseFields(
					subsectionWithPath("_links").description("Links to other resources"),
					subsectionWithPath("_embedded").description("Embedded resources"),
					subsectionWithPath("page").description("Pagination information")
				)
			))
			.andReturn();
	}

	@Test
	@Order(1)
	@WithMockUser(authorities = {"SAVE_MOVIE_GENRE"})
	public void getMovieGenre() throws Exception {
		this.mockMvc.perform(get(entityLinks.linkToItemResource(MovieGenre.class, 1).toUri()))
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movieGenre-get",
				responseFields(
					fieldWithPath("name").description("The name of the movie genre"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			))
			.andReturn();
	}

	@Test
	@Order(2)
	@WithMockUser(authorities = {"SAVE_MOVIE_GENRE"})
	public void putMovieGenre() throws Exception {

		MovieGenre updatedMovieGenre = new MovieGenre();
		updatedMovieGenre.setName("Action Updated");
		this.mockMvc.perform(put(entityLinks.linkToItemResource(MovieGenre.class, 1).toUri())
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(updatedMovieGenre)))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isNoContent())
		.andDo(document("movieGenre-update",
			requestFields(
				fieldWithPath("id").ignored(),
				fieldWithPath("name").description("The name of the movie genre")
			)))
		.andReturn();
	}

	@Test
	@Order(3)
	@WithMockUser(authorities = {"DELETE_MOVIE_GENRE"})
	public void deleteMovieGenre() throws Exception {
		this.mockMvc.perform(delete(entityLinks.linkToItemResource(MovieGenre.class, 1).toUri()))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isNoContent())
		.andDo(document("movieGenre-delete"))
		.andReturn();
	}
}
