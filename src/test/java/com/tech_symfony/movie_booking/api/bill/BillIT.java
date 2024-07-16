package com.tech_symfony.movie_booking.api.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.api.bill.vnpay.VnpayService;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Sql({"/showtime.sql", "/bill.sql"})
@Transactional
public class BillIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private VnpayService vnpayService;


	@Test
	@WithMockUser(username = "admin@gmail.com", password = "test")
	void createBillSuccess() throws Exception {

		BillRequestDTO billRequestDTO = new BillRequestDTO();
		billRequestDTO.setSeatId(List.of(1, 2));
		billRequestDTO.setShowtimeId(1);
		String url = fromMethodCall(on(BillController.class).create(billRequestDTO)).build().toUriString();

		when(vnpayService.doPost(any(Bill.class))).thenReturn("vnpayUrl");
		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(billRequestDTO)))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", notNullValue()))
			.andDo(document("create-bill",
				requestFields(
					fieldWithPath("showtimeId").description("The ID of the showtime"),
					fieldWithPath("seatId").description("The list of seat IDs")
				),
				responseFields(
					fieldWithPath("id").description("The bill's ID"),
					fieldWithPath("total").description("The total amount"),
					fieldWithPath("status").description("The bill's status"),
					fieldWithPath("paymentAt").description("The payment timestamp"),
					fieldWithPath("transactionId").description("The transaction ID"),
					subsectionWithPath("_links").description("Links to other resources")
				),
				links(
					linkWithRel("self").description("Link to this resource").optional(),
					linkWithRel("bill").description("Link to this resource"),
					linkWithRel("bills").description("Link to the bill collection"),
					linkWithRel("pay").optional().description("Link to pay the bill if in progress")
				),
				responseHeaders(
					headerWithName("Location").description("The location of the created showtime")
				)
			));

	}


//	@Test
//	@WithMockUser(username = "testuser")
//	void payBillSuccess() throws Exception {
//		UUID billId = UUID.randomUUID();  // Assume this ID exists in the test database
//
//		this.mockMvc.perform(RestDocumentationRequestBuilders.put("/bills/{id}/payment", billId))
//			.andExpect(status().isOk())
//			.andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
//			.andExpect(jsonPath("$.id").value(billId.toString()))
//			.andExpect(jsonPath("$.total").exists())
//			.andExpect(jsonPath("$.status").value("Paid"))
//			.andExpect(jsonPath("$.paymentAt").exists())
//			.andExpect(jsonPath("$.transactionId").exists())
//			.andExpect(jsonPath("$._links.self.href", containsString("/bills/" + billId)))
//			.andExpect(jsonPath("$._links.bill.href", containsString("/bills")))
//			.andDo(document("pay-bill",
//				pathParameters(
//					parameterWithName("id").description("The bill's ID")
//				),
//				responseFields(
//					fieldWithPath("id").description("The bill's ID").type(JsonFieldType.STRING),
//					fieldWithPath("total").description("The total amount").type(JsonFieldType.NUMBER),
//					fieldWithPath("status").description("The bill's status").type(JsonFieldType.STRING),
//					fieldWithPath("paymentAt").description("The payment timestamp").type(JsonFieldType.STRING),
//					fieldWithPath("transactionId").description("The transaction ID").type(JsonFieldType.STRING),
//					subsectionWithPath("_links").description("Links to other resources")
//				),
//				links(
//					linkWithRel("self").description("Link to this resource"),
//					linkWithRel("bill").description("Link to the bill collection")
//				)
//			));
//	}
//
//	@Test
//	@WithMockUser(username = "testuser")
//	void updateBillStatusSuccess() throws Exception {
//		UUID billId = UUID.randomUUID();  // Assume this ID exists in the test database
//
//		this.mockMvc.perform(RestDocumentationRequestBuilders.put("/bills/{id}", billId))
//			.andExpect(status().isOk())
//			.andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
//			.andExpect(jsonPath("$.id").value(billId.toString()))
//			.andExpect(jsonPath("$.total").exists())
//			.andExpect(jsonPath("$.status").value("Used"))
//			.andExpect(jsonPath("$.paymentAt").exists())
//			.andExpect(jsonPath("$.transactionId").exists())
//			.andExpect(jsonPath("$._links.self.href", containsString("/bills/" + billId)))
//			.andExpect(jsonPath("$._links.bill.href", containsString("/bills")))
//			.andDo(document("update-bill-status",
//				pathParameters(
//					parameterWithName("id").description("The bill's ID")
//				),
//				responseFields(
//					fieldWithPath("id").description("The bill's ID"). type(JsonFieldType.STRING),
//					fieldWithPath("total").description("The total amount").type(JsonFieldType.NUMBER),
//					fieldWithPath("status").description("The bill's status").type(JsonFieldType.STRING),
//					fieldWithPath("paymentAt").description("The payment timestamp").type(JsonFieldType.STRING),
//					fieldWithPath("transactionId").description("The transaction ID").type(JsonFieldType.STRING),
//					subsectionWithPath("_links").description("Links to other resources")
//				),
//				links(
//					linkWithRel("self").description("Link to this resource"),
//					linkWithRel("bill").description("Link to the bill collection")
//				)
//			));
//	}
//
//	@Test
//	@WithMockUser(username = "testuser")
//	void createInProgressBillSuccess() throws Exception {
//		BillRequestDTO billRequestDTO = new BillRequestDTO();
//		// set fields of billRequestDTO as needed to ensure the bill status is IN_PROGRESS
//
//		this.mockMvc.perform(post("/bills")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(billRequestDTO)))
//			.andExpect(status().isCreated())
//			.andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
//			.andExpect(jsonPath("$.id").exists())
//			.andExpect(jsonPath("$.total").exists())
//			.andExpect(jsonPath("$.status").value("IN_PROGRESS"))
//			.andExpect(jsonPath("$.paymentAt").exists())
//			.andExpect(jsonPath("$.transactionId").exists())
//			.andExpect(jsonPath("$._links.self.href", containsString("/bills/")))
//			.andExpect(jsonPath("$._links.bill.href", containsString("/bills")))
//			.andExpect(jsonPath("$._links.pay.href", containsString("/bills/{id}/payment")))
//			.andDo(document("create-in-progress-bill",
//				requestFields(
//					fieldWithPath("field1").description("Description of field1").type(JsonFieldType.STRING),
//					fieldWithPath("field2").description("Description of field2").type(JsonFieldType.STRING)
//					// Add more fields as necessary
//				),
//				responseFields(
//					fieldWithPath("id").description("The bill's ID").type(JsonFieldType.STRING),
//					fieldWithPath("total").description("The total amount").type(JsonFieldType.NUMBER),
//					fieldWithPath("status").description("The bill's status").type(JsonFieldType.STRING),
//					fieldWithPath("paymentAt").description("The payment timestamp").type(JsonFieldType.STRING),
//					fieldWithPath("transactionId").description("The transaction ID").type(JsonFieldType.STRING),
//					subsectionWithPath("_links").description("Links to other resources")
//				),
//				links(
//					linkWithRel("self").description("Link to this resource"),
//					linkWithRel("bill").description("Link to the bill collection"),
//					linkWithRel("pay").description("Link to pay the bill if it is in progress")
//				)
//			));
//	}
//}
//
//
//
//	@Test
//	void createBill_Successful() throws Exception {
//		BillRequestDTO billRequestDTO = new BillRequestDTO();
//		billRequestDTO.setShowtimeId(1);
//		billRequestDTO.setSeatId(Collections.singletonList(1));
//
//		Bill bill = new Bill();
//		bill.setId(UUID.randomUUID());
//		bill.setTotal(200.0);
//		bill.setStatus(BillStatus.HOLDING);
//
//		given(billService.create(any(BillRequestDTO.class), anyString())).willReturn(bill);
//
//		this.mockMvc.perform(post("/api/bills")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(billRequestDTO)))
//			.andExpect(status().isOk())
//			.andDo(document("create-bill",
//				requestFields(
//					fieldWithPath("showtimeId").description("The ID of the showtime"),
//					fieldWithPath("seatId").description("List of seat IDs")
//				),
//				responseFields(
//					fieldWithPath("id").description("The ID of the created bill"),
//					fieldWithPath("total").description("The total cost of the bill"),
//					fieldWithPath("status").description("The status of the bill")
//				)));
//	}
//
//	@Test
//	void payBill_Successful() throws Exception {
//		UUID billId = UUID.randomUUID();
//		Bill bill = new Bill();
//		bill.setId(billId);
//		bill.setStatus(BillStatus.COMPLETED);
//
//		given(billService.pay(any(UUID.class), anyString())).willReturn(bill);
//
//		this.mockMvc.perform(put("/api/bills/{billId}/pay", billId)
//				.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(document("pay-bill",
//				pathParameters(
//					parameterWithName("billId").description("The ID of the bill to pay")
//				),
//				responseFields(
//					fieldWithPath("id").description("The ID of the bill"),
//					fieldWithPath("status").description("The status of the bill")
//				)));
//	}
//
//	@Test
//	void updateBillStatus_Successful() throws Exception {
//		UUID billId = UUID.randomUUID();
//		Bill bill = new Bill();
//		bill.setId(billId);
//		bill.setStatus(BillStatus.COMPLETED);
//
//		given(billService.updateStatus(any(UUID.class))).willReturn(bill);
//
//		this.mockMvc.perform(put("/api/bills/{billId}/status", billId)
//				.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(document("update-bill-status",
//				pathParameters(
//					parameterWithName("billId").description("The ID of the bill to update")
//				),
//				responseFields(
//					fieldWithPath("id").description("The ID of the bill"),
//					fieldWithPath("status").description("The new status of the bill")
//				)));
//	}

}
