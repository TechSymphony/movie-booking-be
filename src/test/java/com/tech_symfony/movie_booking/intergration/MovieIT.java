package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.movie.*;
import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import com.tech_symfony.movie_booking.system.config.ESTestConfiguration;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@SpringBootTest(classes = MovieBookingApplication.class)
@Import(ESTestConfiguration.class)
@Sql("/movie.sql")
public class MovieIT extends BaseIntegrationTest {

//	@Autowired
//	private ObjectMapper objectMapper;

	@MockBean
	private MovieService movieService;

	@MockBean
	private MovieRepository movieRepository;

	@MockBean
	private MovieModelAssembler movieModelAssembler;

	@Test
	public void getMovies() throws Exception {
		this.mockMvc.perform(get(fromMethodCall(on(MovieController.class).getAllMovies()).build().toUri()))
			.andDo(response -> {
				System.out.println(response.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-getAll"));
	}

	@Test
	public void getMovieById() throws Exception {
			this.mockMvc.perform(get(fromMethodCall(
				on(MovieController.class).getMovieById(111)).build().toUri()
				))
			.andDo(response -> {
				System.out.println(response.getResponse().getContentAsString());
			})
			.andExpect(status().isOk())
			.andDo(document("movies-get",
				pathParameters(
					parameterWithName("movieId").description("The ID of the movie")
				)
			));
	}

//	@Test
//	public void deleteMovie() throws Exception {
//		MockMvcRequestBuilders.get()
//		this.mockMvc.perform(delete("/movies/{movieId}", 1))
//			.andExpect(status().isOk())
//			.andDo(document("movies-delete",
//				pathParameters(
//					parameterWithName("id").description("The ID of the movie")
//				)
//			));
//	}
}
