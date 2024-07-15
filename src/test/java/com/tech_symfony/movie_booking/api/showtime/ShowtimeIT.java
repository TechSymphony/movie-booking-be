package com.tech_symfony.movie_booking.api.showtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@Sql("/showtime.sql")
public class ShowtimeIT extends BaseIntegrationTest {
	@Autowired
	private EntityLinks entityLinks;
	@Autowired
	private ObjectMapper objectMapper;

	private URI itemURL;
	private URI collectionURL;
	private URI movieItemURL;
	private URI roomItemURL;
	private Room room;
	private Movie movie;


	@Setter
	@Getter
	private class ShowtimeRequestDTO {
		URI room;
		URI movie;
		int runningTime;
		Time startTime;
		LocalDateTime startDate;

	}

	@BeforeEach
	public void setUp() {
		itemURL = entityLinks.linkToItemResource(Showtime.class, 1).toUri();
		collectionURL = entityLinks.linkToCollectionResource(Showtime.class).toUri();
		movieItemURL = entityLinks.linkToItemResource(Movie.class, 1).toUri();
		roomItemURL = entityLinks.linkToItemResource(Room.class, 1).toUri();
	}

	@Test
	public void testCreateShowtime() throws Exception {


		ShowtimeRequestDTO showtimeRequestDTO = new ShowtimeRequestDTO();
		showtimeRequestDTO.setRunningTime(140);
		showtimeRequestDTO.setRoom(roomItemURL);
		showtimeRequestDTO.setMovie(movieItemURL);
		showtimeRequestDTO.setStartDate(LocalDateTime.now().plusDays(1L));
		showtimeRequestDTO.setStartTime(Time.valueOf(LocalTime.parse("11:14:05")));
		ResultActions resultActions = this.mockMvc.perform(get(roomItemURL))
			.andExpect(status().isOk());
		this.mockMvc.perform(post(collectionURL)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(showtimeRequestDTO)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", notNullValue()))
			.andDo(document("create-showtime",
				requestFields(
					fieldWithPath("startDate").description("The start date of the showtime"),
					fieldWithPath("startTime").description("The start time of the showtime"),
					fieldWithPath("runningTime").description("The running time of the showtime"),
					fieldWithPath("status").description("The status of the showtime").optional(),
					fieldWithPath("movie").description("The title of the movie"),
					fieldWithPath("room").description("The name of the room")
				),
				responseFields(
					fieldWithPath("startDate").description("The start date of the showtime"),
					fieldWithPath("startTime").description("The start time of the showtime"),
					fieldWithPath("runningTime").description("The running time of the showtime"),
					fieldWithPath("status").description("The status of the showtime"),
					fieldWithPath("movie").description("The title of the movie"),
					fieldWithPath("room").description("The name of the room")
				),
				responseHeaders(
					headerWithName("Location").description("The location of the created showtime")
				)));
		int a = 3;
	}
//
//	@Test
//	public void testGetShowtime() throws Exception {
//		this.mockMvc.perform(post(itemURL)
//				.accept(MediaTypes.HAL_JSON))
//			.andExpect(status().isOk())
//			.andDo(document("get-showtime",
//				responseFields(
//					fieldWithPath("startDate").description("The start date of the showtime"),
//					fieldWithPath("startTime").description("The start time of the showtime"),
//					fieldWithPath("runningTime").description("The running time of the showtime"),
//					fieldWithPath("status").description("The status of the showtime"),
//					fieldWithPath("movie").description("The title of the movie"),
//					fieldWithPath("room").description("The name of the room")
//				)));
//	}
//
//	@Test
//	public void testGetCollectionShowtime() throws Exception {
//		this.mockMvc.perform(post(itemURL)
//				.accept(MediaTypes.HAL_JSON))
//			.andExpect(status().isOk())
//			.andDo(document("get-collection-showtime",
//				responseFields(
//					fieldWithPath("_embedded.showtimes").description("The start date of the showtime")
//				)));
//	}
//
//	@Test
//	public void testUpdateShowtime() throws Exception {
//		String showtimeJson = "{ \"startDate\": \"2023-07-14\", \"startTime\": \"16:00:00\", \"runningTime\": 130, \"status\": false, \"movie\": \"Updated Movie\", \"room\": \"Room 2\" }";
//
//		this.mockMvc.perform(put(itemURL)
//				.contentType(MediaTypes.HAL_JSON)
//				.content(showtimeJson))
//			.andExpect(status().isOk())
//			.andDo(document("update-showtime",
//				requestFields(
//					fieldWithPath("startDate").description("The start date of the showtime"),
//					fieldWithPath("startTime").description("The start time of the showtime"),
//					fieldWithPath("runningTime").description("The running time of the showtime"),
//					fieldWithPath("status").description("The status of the showtime"),
//					fieldWithPath("movie").description("The title of the movie"),
//					fieldWithPath("room").description("The name of the room")
//				),
//				responseFields(
//					fieldWithPath("startDate").description("The start date of the showtime"),
//					fieldWithPath("startTime").description("The start time of the showtime"),
//					fieldWithPath("runningTime").description("The running time of the showtime"),
//					fieldWithPath("status").description("The status of the showtime"),
//					fieldWithPath("movie").description("The title of the movie"),
//					fieldWithPath("room").description("The name of the room")
//				)));
//	}
//
//	@Test
//	public void testDeleteShowtime() throws Exception {
//		this.mockMvc.perform(delete(itemURL))
//			.andExpect(status().isNoContent())
//			.andDo(document("delete-showtime"));
//	}

}
