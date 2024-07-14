package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieGenreIT extends BaseIntegrationTest {

	@Autowired
	EntityLinks entityLinks;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getMovieGenres() throws Exception {
		this.mockMvc.perform(get(entityLinks.linkToCollectionResource(MovieGenre.class).toUri()))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isOk())
		.andDo(document("movieGenre-get-all"))
		.andReturn();
	}

	@Test
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
		.andDo(document("movieGenre-post"))
		.andReturn();

		this.mockMvc.perform(get(entityLinks.linkToItemResource(MovieGenre.class, 1).toUri()))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isOk())
		.andDo(document("movieGenre-get"))
		.andReturn();
	}

	@Test
	@WithMockUser(authorities = {"SAVE_MOVIE_GENRE"})
	public void putMovieGenre() throws Exception {

		MovieGenre movieGenre = new MovieGenre();
		movieGenre.setName("Action");

		this.mockMvc.perform(put(entityLinks.linkToItemResource(MovieGenre.class, 1).toUri())
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(movieGenre)))
		.andDo(result -> {
			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
		})
		.andExpect(status().isCreated())
		.andDo(document("movieGenre-post"))
		.andReturn();
	}

	@Test
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
