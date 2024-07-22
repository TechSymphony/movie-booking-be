package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.seat.SeatRepository;

import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

@SpringBootTest(classes = MovieBookingApplication.class)

public class SeatIT extends BaseIntegrationTest {
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SeatRepository seatRepository;

	@Test
	public void getById() throws Exception {
		Seat seat = new Seat();
		seat.setId(1);
		seat.setIsReserved(true);
		seat.setStatus(true);
		seat.setType(null);
		seat.setRowName("A");
		seat.setRowIndex(1);
		seat.setTickets(Collections.emptySet());

		given(seatRepository.getById(1)).willReturn(seat);

		this.mockMvc.perform(get("/seats/{id}", 1)
				.accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("seats-get-by-id",
				pathParameters(
					parameterWithName("id").description("The ID of the seat")
				),
				responseFields(
					fieldWithPath("id").description("The ID of the seat"),
					fieldWithPath("status").description("The status of the seat"),
					fieldWithPath("row_name").description("The row name of the seat"),
					fieldWithPath("row_index").description("The row index of the seat"),
					fieldWithPath("room").description("The room of the seat"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			));
	}

	@Test
	public void createSeat() throws Exception {
		Seat seat = new Seat();
		seat.setId(1);
		seat.setIsReserved(true);
		seat.setStatus(true);
		seat.setType(null);
		seat.setRowName("A");
		seat.setRowIndex(1);
		seat.setTickets(Collections.emptySet());

		given(seatRepository.save(seat)).willReturn(seat);

		this.mockMvc.perform(post("/seats")
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(seat)))
			.andExpect(status().isCreated())
			.andDo(document("seats-create",
				requestFields(
					fieldWithPath("id").description("The ID of the seat").optional(),
					fieldWithPath("status").description("The status of the seat"),
					fieldWithPath("type").description("The type of the seat").optional(),
					fieldWithPath("row_index").description("The row index of the seat"),
					fieldWithPath("room").description("The room of the seat"),
					fieldWithPath("row_name").description("The row name of the seat")
				)
			));
	}

	@Test
	public void updateSeat() throws Exception {
		Seat seat = new Seat();
		seat.setId(1);
		seat.setIsReserved(true);
		seat.setStatus(true);
		seat.setType(null);
		seat.setRowName("A");
		seat.setRowIndex(1);
		seat.setTickets(Collections.emptySet());

		given(seatRepository.save(seat)).willReturn(seat);

		this.mockMvc.perform(put("/seats/{id}",1)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(seat)))
			.andExpect(status().isCreated())
			.andDo(document("seats-update",
				requestFields(
					fieldWithPath("id").description("The ID of the seat").optional(),
					fieldWithPath("status").description("The status of the seat"),
					fieldWithPath("type").description("The type of the seat").optional(),
					fieldWithPath("row_index").description("The row index of the seat"),
					fieldWithPath("room").description("The room of the seat"),
					fieldWithPath("row_name").description("The row name of the seat")
				)
			));
	}

}
