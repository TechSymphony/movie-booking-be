package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.seat_type.SeatType;
import com.tech_symfony.movie_booking.api.seat_type.SeatTypeRepository;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieBookingApplication.class)
public class SeatTypeIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SeatTypeRepository seatTypeRepository;

	@Test
	public void getById() throws Exception {
		SeatType seatType = new SeatType();
		seatType.setId(1);
		seatType.setName("VIP");
		seatType.setPrice(150.0);

		given(seatTypeRepository.getById(1)).willReturn(seatType);

		this.mockMvc.perform(get("/seat_types/{id}", 1)
				.accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("seat_types-get-by-id",
				pathParameters(
					parameterWithName("id").description("The ID of the seat type")
				),
				responseFields(
					fieldWithPath("id").description("The ID of the seat type"),
					fieldWithPath("name").description("The name of the seat type"),
					fieldWithPath("price").description("The price of the seat type"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			));
	}

	@Test
	public void createSeatType() throws Exception {
		SeatType seatType = new SeatType();
		seatType.setId(1);
		seatType.setName("VIP");
		seatType.setPrice(150.0);

		given(seatTypeRepository.save(seatType)).willReturn(seatType);

		this.mockMvc.perform(post("/seat_types")
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(seatType)))
			.andExpect(status().isCreated())
			.andDo(document("seat_types-create",
				requestFields(
					fieldWithPath("id").description("The ID of the seat type").optional(),
					fieldWithPath("name").description("The name of the seat type"),
					fieldWithPath("price").description("The price of the seat type")
				)
			));
	}

	@Test
	public void updateSeatType() throws Exception {
		SeatType seatType = new SeatType();
		seatType.setId(1);
		seatType.setName("VIP");
		seatType.setPrice(150.0);

		given(seatTypeRepository.save(seatType)).willReturn(seatType);

		this.mockMvc.perform(put("/seat_types/{id}", 1)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(seatType)))
			.andExpect(status().isCreated())
			.andDo(document("seat_types-update",
				requestFields(
					fieldWithPath("id").description("The ID of the seat type").optional(),
					fieldWithPath("name").description("The name of the seat type"),
					fieldWithPath("price").description("The price of the seat type")
				)
			));
	}
}
