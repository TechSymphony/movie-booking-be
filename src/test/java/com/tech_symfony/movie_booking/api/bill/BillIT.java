package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MovieBookingApplication.class)
public class BillIT extends BaseIntegrationTest {


//	@Autowired
//	private ObjectMapper objectMapper;
//	@Autowired
//	private VnpayService vnpayService;
//
//	@InjectMocks
//	private DefaultBillService billService;
//	private static BillRequestDTO billRequestDTO;
//	private static Showtime showtime;
//	private static List<Seat> seatList;
//	private static User user;
//
//	private static UUID existingBillId;
//	private static Bill holdingBill;
//	private static Bill completedBill;
//
//	@BeforeAll
//	public static void setUp() {
//
//		user = new User();
//		user.setEmail("user@example.com");
//		Room room = new Room();
//		room.setId(1);
//		showtime = new Showtime();
//		showtime.setId(1);
//		showtime.setRoom(room);
//
//		SeatType seatType = new SeatType();
//		seatType.setPrice(100.0);
//		seatType.setId(1);
//		seatList = new ArrayList<>();
//		Seat seat1 = new Seat();
//		seat1.setId(2);
//		seat1.setType(seatType);
//		seat1.setRoom(room);
//		Seat seat2 = new Seat();
//		seat2.setId(3);
//		seat2.setType(seatType);
//		seat1.setRoom(room);
//		seatList.add(seat1);
//		seatList.add(seat2);
//
//
//		billRequestDTO = new BillRequestDTO();
//		billRequestDTO.setShowtimeId(showtime.getId());
//		billRequestDTO.setSeatId(Arrays.asList(2, 3));
//
//		existingBillId = UUID.randomUUID();
//		holdingBill = new Bill();
//		holdingBill.setId(existingBillId);
//		holdingBill.setStatus(BillStatus.HOLDING);
//
//		completedBill = new Bill();
//		completedBill.setId(existingBillId);
//		completedBill.setStatus(BillStatus.COMPLETED);
//
//	}
//	@Test
//	@WithMockUser(username = "testuser")
//	void createBillSuccess() throws Exception {
//		BillRequestDTO billRequestDTO = new BillRequestDTO();
//		// set fields of billRequestDTO as needed
//
//		this.mockMvc.perform(post("/bills")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(billRequestDTO)))
//			.andExpect(status().isCreated())
//			.andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
//			.andExpect(jsonPath("$.id").exists())
//			.andExpect(jsonPath("$.total").exists())
//			.andExpect(jsonPath("$.status").value("Unpaid"))
//			.andExpect(jsonPath("$.paymentAt").exists())
//			.andExpect(jsonPath("$.transactionId").exists())
//			.andExpect(jsonPath("$._links.self.href", containsString("/bills/")))
//			.andExpect(jsonPath("$._links.bill.href", containsString("/bills")))
//			.andDo(document("create-bill",
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
//					linkWithRel("pay").optional().description("Link to pay the bill if in progress")
//				)
//			));
//	}
//
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
