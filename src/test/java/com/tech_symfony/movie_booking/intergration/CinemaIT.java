package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.cinema.CinemaStatus;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/cinema.sql")
public class CinemaIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	EntityLinks entityLinks;

	@Test
	@WithMockUser(username = "testuser", authorities = {"READ_CINEMA"})
	@Transactional
	public void getById() throws Exception {
		this.mockMvc.perform(get("/cinemas/{id}",1)
				.accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("cinemas-get-by-id",
				pathParameters(
					parameterWithName("id").description("The ID of the cinema")
				),
				responseFields(
					fieldWithPath("name").description("The name of the cinema"),
					fieldWithPath("address").description("The address of the cinema"),
					fieldWithPath("district").description("The district of the cinema"),
					fieldWithPath("city").description("The city of the cinema"),
					fieldWithPath("description").description("The description of the cinema"),
					fieldWithPath("status").description("The status of the cinema"),
					fieldWithPath("phoneNumber").description("The phone number of the cinema"),
					fieldWithPath("rooms").description("The rooms of the cinema").type(JsonFieldType.ARRAY),
					fieldWithPath("rooms[].name").description("The name of the room"),
					fieldWithPath("rooms[].totalSeats").description("The total number of seats in the room"),
					fieldWithPath("rooms[].availableSeats").description("The number of available seats in the room"),
					fieldWithPath("rooms[].slug").description("The slug of the room"),
					fieldWithPath("rooms[].showtimes").description("The showtimes for the room").type(JsonFieldType.ARRAY),
					subsectionWithPath("rooms[].showtimes[]").description("The showtimes for the room"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			));
	}

	@Test
	@Transactional
	@WithMockUser(username = "testuser", authorities = {"SAVE_CINEMA"})
	public void createCinema() throws Exception {
		Cinema cinema = new Cinema();
		cinema.setName("Test Cinema");
		cinema.setAddress("123 Test St");
		cinema.setDistrict("Test District");
		cinema.setCity("Test City");
		cinema.setDescription("Test Description");
		cinema.setStatus(CinemaStatus.OPENING);
		cinema.setPhoneNumber("0123456789");
		cinema.setRooms(Collections.emptySet());

//		System.out.println(objectMapper.writeValueAsString(cinema));


		this.mockMvc.perform(post("/cinemas")
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(cinema)))
//			.andExpect(status().isCreated())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("cinemas-create",
				requestFields(
					fieldWithPath("id").description("The ID of the cinema").optional(),
					fieldWithPath("name").description("The name of the cinema"),
					fieldWithPath("address").description("The address of the cinema"),
					fieldWithPath("district").description("The district of the cinema"),
					fieldWithPath("city").description("The city of the cinema"),
					fieldWithPath("description").description("The description of the cinema"),
					fieldWithPath("status").description("The status of the cinema"),
					fieldWithPath("phoneNumber").description("The phone number of the cinema"),
					fieldWithPath("rooms").description("The rooms of the cinema")
				)
			));
	}

	@Test
	@Transactional
	@WithMockUser(username = "testuser", authorities = {"SAVE_CINEMA"})
	public void updateCinema() throws Exception {
		Cinema cinema = new Cinema();
		cinema.setId(1);
		cinema.setName("Test Cinema");
		cinema.setAddress("123 Test St");
		cinema.setDistrict("Test District");
		cinema.setCity("Test City");
		cinema.setDescription("Test Description");
		cinema.setStatus(CinemaStatus.OPENING);
		cinema.setPhoneNumber("0123456789");
		cinema.setRooms(Collections.emptySet());



		this.mockMvc.perform(put("/cinemas/{id}", 1)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(cinema)))
//			.andExpect(status().isCreated())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("cinemas-update",
				requestFields(
					fieldWithPath("id").description("The ID of the cinema").optional(),
					fieldWithPath("name").description("The name of the cinema"),
					fieldWithPath("address").description("The address of the cinema"),
					fieldWithPath("district").description("The district of the cinema"),
					fieldWithPath("city").description("The city of the cinema"),
					fieldWithPath("description").description("The description of the cinema"),
					fieldWithPath("status").description("The status of the cinema"),
					fieldWithPath("phoneNumber").description("The phone number of the cinema"),
					fieldWithPath("rooms").description("The rooms of the cinema")
				)
			));
	}

	@Test
	@WithMockUser(username = "testuser", authorities = {"DELETE_CINEMA"})
	@Transactional
	public void deleteCinema() throws Exception {
		this.mockMvc.perform(delete("/cinemas/{id}",1)
				.accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("cinemas-delete-by-id",
				pathParameters(
					parameterWithName("id").description("The ID of the cinema")
				)
			));
	}
}

