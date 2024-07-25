//package com.tech_symfony.movie_booking.intergration;
//
//import com.tech_symfony.movie_booking.api.movie.elastic.MovieElasticController;
//import com.tech_symfony.movie_booking.api.movie.elastic.MovieSearch;
//import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
//import com.tech_symfony.movie_booking.system.config.ESTestConfiguration;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//
//import java.sql.Date;
//import java.util.Optional;
//
//import static org.slf4j.MDC.get;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
//import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;
//
//public class MovieElasticIT extends BaseIntegrationTest {
//
//	@Autowired
//	private ElasticsearchOperations operations;
//
//	@BeforeEach
//	public void setup() {
//		MovieSearch movieSearch = new MovieSearch();
//		movieSearch.setId(1);
//		movieSearch.setCode("mv-0002");
//		movieSearch.setName("The Godfather");
//		movieSearch.setSubName("The Godfather");
//		movieSearch.setDirector("Francis Ford Coppola");
//		movieSearch.setCaster("Mario Puzo");
//		movieSearch.setReleaseDate(Date.valueOf("1972-03-24"));
//		movieSearch.setEndDate(Date.valueOf("1972-03-24"));
//		movieSearch.setRunningTime(175);
//		movieSearch.setLanguage("English");
//		movieSearch.setNumberOfRatings(100);
//		movieSearch.setSumOfRatings(1000);
//		movieSearch.setDescription("The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.");
//		movieSearch.setPoster("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
//		movieSearch.setHorizontalPoster("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg");
//		movieSearch.setTrailer("https://www.youtube.com/watch?v=sY1S34973zA");
//		movieSearch.setRated(5);
//		movieSearch.setSlug("the-godfather");
//		operations.save(movieSearch);
//	}
//	@Test
//	public void getMovieByName() throws Exception {
//
//		this.mockMvc.perform(RestDocumentationRequestBuilders.get(fromMethodCall(on(MovieElasticController.class).search(Pageable.unpaged(), Optional.of(""))).build().toUriString()
//			+"?name=The"))
//		.andDo(result -> {
//			System.out.println("Response JSON: " + result.getResponse().getContentAsString());
//		})
//		.andExpect(status().isOk())
//			.andDo(document("movieGenre-post", requestFields(
//				fieldWithPath("id").ignored(),
//				fieldWithPath("code").description("The code of the movie"),
//				fieldWithPath("name").description("The name of the movie"),
//				fieldWithPath("subName").description("The sub name of the movie"),
//				fieldWithPath("director").description("The director of the movie"),
//				fieldWithPath("caster").description("The caster of the movie"),
//				fieldWithPath("releaseDate").description("The release date of the movie"),
//				fieldWithPath("endDate").description("The end date of the movie"),
//				fieldWithPath("runningTime").description("The running time of the movie"),
//				fieldWithPath("language").description("The language of the movie"),
//				fieldWithPath("numberOfRatings").description("The number of ratings of the movie"),
//				fieldWithPath("sumOfRatings").description("The sum of ratings of the movie"),
//				fieldWithPath("description").description("The description of the movie"),
//				fieldWithPath("poster").description("The poster of the movie"),
//				fieldWithPath("horizontalPoster").description("The horizontal poster of the movie"),
//				fieldWithPath("trailer").description("The trailer of the movie"),
//				fieldWithPath("rated").description("The rated of the movie"),
//				fieldWithPath("slug").description("The slug of the movie")
//			)));
//	}
//}
