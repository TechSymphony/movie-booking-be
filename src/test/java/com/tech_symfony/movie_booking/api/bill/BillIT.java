package com.tech_symfony.movie_booking.api.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.nimbusds.jose.shaded.gson.Gson;
import com.tech_symfony.movie_booking.api.bill.vnpay.VnpayService;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@WithMockUser(username = "admin@gmail.com", password = "test", authorities = "SAVE_BILL")
public class BillIT extends BaseIntegrationTest {

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private VnpayService vnpayService;
	private static UUID billID;

	@Test
	@Order(1)
	@Sql({"/showtime.sql", "/bill.sql"})
	void createBillSuccess() throws Exception {

		BillRequestDTO billRequestDTO = new BillRequestDTO();
		billRequestDTO.setSeatId(List.of(1, 2));
		billRequestDTO.setShowtimeId(1);
		String url = fromMethodCall(on(BillController.class).create(billRequestDTO)).build().toUriString();

		when(vnpayService.doPost(any(Bill.class))).thenReturn("vnpayUrl");
		MvcResult result = this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(billRequestDTO)))
			.andExpect(jsonPath("$.status").value(BillStatus.IN_PROGRESS.toString()))
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
			)).andReturn();
		String content = result.getResponse().getContentAsString();
		billID = UUID.fromString(JsonPath.read(content, "$.id"));
	}

	@Test
	@Order(2)
	void payBillSuccess() throws Exception {
		String url = fromMethodCall(on(BillController.class).pay(billID)).build().toUriString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vnp_TransactionNo", "12345");

		when(vnpayService.verifyPay(any(Bill.class))).thenReturn(jsonObject);
		this.mockMvc.perform(put(url))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.transactionId").value("12345"))
			.andExpect(jsonPath("$.status").value(BillStatus.HOLDING.toString()))
			.andDo(document("pay-bill",
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
					linkWithRel("bills").description("Link to the bill collection")
				)
			))
		;


	}

	//
	@Test
	@Order(3)
	void updateBillStatusSuccess() throws Exception {
		String url = fromMethodCall(on(BillController.class).updateStatus(billID)).build().toUriString();
		this.mockMvc.perform(put(url))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(BillStatus.COMPLETED.toString()))
			.andDo(document("complete-bill",
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
					linkWithRel("bills").description("Link to the bill collection")
				)
			));
	}


}
