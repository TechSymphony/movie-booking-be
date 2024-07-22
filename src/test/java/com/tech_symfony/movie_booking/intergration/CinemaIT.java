package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.api.cinema.CinemaRepository;
import com.tech_symfony.movie_booking.api.cinema.CinemaStatus;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import java.util.Collections;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieBookingApplication.class)
public class CinemaIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CinemaRepository cinemaRepository;

	@Test
	public void getById() throws Exception {
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

		given(cinemaRepository.getById(1)).willReturn(cinema);

		this.mockMvc.perform(get("/cinemas/{id}", 1)
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
					fieldWithPath("id").description("The ID of the cinema"),
					fieldWithPath("name").description("The name of the cinema"),
					fieldWithPath("address").description("The address of the cinema"),
					fieldWithPath("district").description("The district of the cinema"),
					fieldWithPath("city").description("The city of the cinema"),
					fieldWithPath("description").description("The description of the cinema"),
					fieldWithPath("status").description("The status of the cinema"),
					fieldWithPath("phoneNumber").description("The phone number of the cinema"),
					fieldWithPath("rooms").description("The rooms of the cinema"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			));
	}

	@Test
	public void createCinema() throws Exception {
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

		given(cinemaRepository.save(cinema)).willReturn(cinema);

		this.mockMvc.perform(post("/cinemas")
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(cinema)))
			.andExpect(status().isCreated())
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

		given(cinemaRepository.save(cinema)).willReturn(cinema);

		this.mockMvc.perform(put("/cinemas/{id}", 1)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(cinema)))
			.andExpect(status().isCreated())
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
}

