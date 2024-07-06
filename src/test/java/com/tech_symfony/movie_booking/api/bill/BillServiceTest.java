package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.bill.vnpay.VnpayService;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.seat.SeatRepository;
import com.tech_symfony.movie_booking.api.seat_type.SeatType;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.api.ticket.TicketRepository;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.api.user.UserRepository;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith({MockitoExtension.class})
public class BillServiceTest extends BaseUnitTest {

	@Mock
	private BillRepository billRepository;

	@Mock
	private ShowtimeRepository showtimeRepository;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private SeatRepository seatRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private VnpayService vnpayService;

	@InjectMocks
	private DefaultBillService billService;
	private static BillRequestDTO billRequestDTO;
	private static Showtime showtime;
	private static List<Seat> seatList;
	private static User user;

	@BeforeAll
	public static void setUp() {

		user = new User();
		user.setEmail("user@example.com");
		Room room = new Room();
		room.setId(1);
		showtime = new Showtime();
		showtime.setId(1);
		showtime.setRoom(room);

		SeatType seatType = new SeatType();
		seatType.setPrice(100.0);
		seatType.setId(1);
		seatList = new ArrayList<>();
		Seat seat1 = new Seat();
		seat1.setId(2);
		seat1.setType(seatType);
		seat1.setRoom(room);
		Seat seat2 = new Seat();
		seat2.setId(3);
		seat2.setType(seatType);
		seat1.setRoom(room);
		seatList.add(seat1);
		seatList.add(seat2);


		billRequestDTO = new BillRequestDTO();
		billRequestDTO.setShowtimeId(showtime.getId());
		billRequestDTO.setSeatId(Arrays.asList(2, 3));
	}

	@Test
	void create_ShouldThrowResourceNotFoundException_WhenShowtimeDoesNotExist() {


		when(showtimeRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			billService.create(billRequestDTO, user.getEmail());
		});
	}

	@Test
	void create_ShouldThrowResourceNotFoundException_WhenSeatsDoNotExist() {


		when(showtimeRepository.findById(showtime.getId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(anyList(), any(Integer.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			billService.create(billRequestDTO, user.getEmail());
		});
	}

	@Test
	void create_ShouldThrowException_WhenUserNotFound() {


		when(showtimeRepository.findById(billRequestDTO.getShowtimeId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId())).thenReturn(Optional.of(seatList));
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
			billService.create(billRequestDTO, user.getEmail());
		});

		assertEquals("Conflict", exception.getMessage());
	}

	@Test
	void create_ShouldReturnBill_WhenSuccessful() {


		when(showtimeRepository.findById(billRequestDTO.getShowtimeId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId())).thenReturn(Optional.of(seatList));

		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> invocation.getArgument(0));
		when(vnpayService.doPost(any(Bill.class))).thenReturn("vnpayUrl");

		Bill bill = billService.create(billRequestDTO, user.getEmail());

		assertNotNull(bill);
		assertEquals(200.0, bill.getTotal());
		assertEquals("vnpayUrl", bill.getVnpayUrl());
		verify(billRepository, times(1)).save(any(Bill.class));
	}

	@Test
	void pay_ShouldThrowException_WhenBillNotFound() {
		UUID billID = UUID.randomUUID();

		when(billRepository.findByIdAndUser(billID, any(User.class))).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			billService.pay(billID, user.getEmail());
		});

		assertEquals("Bill is not exits", exception.getMessage());
	}

	@Test
	void pay_ShouldReturnUpdatedBill_WhenSuccessful() {
		UUID billID = UUID.randomUUID();
		Bill bill = new Bill();
		bill.setId(billID);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vnp_TransactionNo", "12345");

		when(billRepository.findById(billID)).thenReturn(Optional.of(bill));
		when(vnpayService.verifyPay(bill)).thenReturn(jsonObject);
		when(billRepository.save(bill)).thenAnswer(invocation -> invocation.getArgument(0));

		Bill updatedBill = billService.pay(billID, user.getEmail());

		assertNotNull(updatedBill);
		assertEquals(BillStatus.HOLDING, updatedBill.getStatus());
		assertNotNull(updatedBill.getPaymentAt());
		assertEquals("12345", updatedBill.getTransactionId());
		verify(billRepository, times(1)).save(bill);
	}
//	@Test
//	void create_ShouldThrowException_WhenSeatsNotFound() {
//		UUID showtimeId = UUID.randomUUID();
//		BillDTO billDTO = new BillDTO();
//		billDTO.setShowtimeId(showtimeId);
//		billDTO.setSeatId(List.of(UUID.randomUUID()));
//
//		Showtime showtime = new Showtime();
//		showtime.setId(showtimeId);
//
//		when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
//		when(seatRepository.findAllById(billDTO.getSeatId())).thenReturn(List.of());
//
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			billService.create(billDTO, "user@example.com");
//		});
//
//		assertEquals("Seats not found", exception.getMessage());
//	}
//
//	@Test
//	void create_ShouldThrowException_WhenUserNotFound() {
//		UUID showtimeId = UUID.randomUUID();
//		BillDTO billDTO = new BillDTO();
//		billDTO.setShowtimeId(showtimeId);
//		billDTO.setSeatId(List.of(UUID.randomUUID()));
//
//		Showtime showtime = new Showtime();
//		showtime.setId(showtimeId);
//		Seat seat = new Seat();
//		seat.setId(UUID.randomUUID());
//
//		when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
//		when(seatRepository.findAllById(billDTO.getSeatId())).thenReturn(List.of(seat));
//		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
//
//		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
//			billService.create(billDTO, "user@example.com");
//		});
//
//		assertEquals("Conflict", exception.getMessage());
//	}
//
//	@Test
//	void create_ShouldReturnBill_WhenSuccessful() {
//		UUID showtimeId = UUID.randomUUID();
//		BillDTO billDTO = new BillDTO();
//		billDTO.setShowtimeId(showtimeId);
//		billDTO.setSeatId(List.of(UUID.randomUUID()));
//
//		Showtime showtime = new Showtime();
//		showtime.setId(showtimeId);
//		Seat seat = new Seat();
//		seat.setId(UUID.randomUUID());
//		seat.setType(new SeatType(100.0));
//		User user = new User();
//		user.setEmail("user@example.com");
//
//		when(showtimeRepository.findById(showtimeId)).thenReturn(Optional.of(showtime));
//		when(seatRepository.findAllById(billDTO.getSeatId())).thenReturn(List.of(seat));
//		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
//		when(billRepository.save(any(Bill.class))).thenAnswer(invocation -> invocation.getArgument(0));
//		when(vnpayService.doPost(any(Bill.class))).thenReturn("vnpayUrl");
//
//		Bill bill = billService.create(billDTO, "user@example.com");
//
//		assertNotNull(bill);
//		assertEquals(100.0, bill.getTotal());
//		assertEquals("vnpayUrl", bill.getVnpayUrl());
//		verify(billRepository, times(1)).save(any(Bill.class));
//	}
//
//	@Test
//	void pay_ShouldThrowException_WhenBillNotFound() {
//		UUID billID = UUID.randomUUID();
//
//		when(billRepository.findById(billID)).thenReturn(Optional.empty());
//
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			billService.pay(billID);
//		});
//
//		assertEquals("Bill is not exits", exception.getMessage());
//	}
//
//	@Test
//	void pay_ShouldReturnUpdatedBill_WhenSuccessful() {
//		UUID billID = UUID.randomUUID();
//		Bill bill = new Bill();
//		bill.setId(billID);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("vnp_TransactionNo", "12345");
//
//		when(billRepository.findById(billID)).thenReturn(Optional.of(bill));
//		when(vnpayService.verifyPay(bill)).thenReturn(jsonObject);
//		when(billRepository.save(bill)).thenAnswer(invocation -> invocation.getArgument(0));
//
//		Bill updatedBill = billService.pay(billID);
//
//		assertNotNull(updatedBill);
//		assertEquals(BillStatus.HOLDING, updatedBill.getStatus());
//		assertNotNull(updatedBill.getPaymentAt());
//		assertEquals("12345", updatedBill.getTransactionId());
//		verify(billRepository, times(1)).save(bill);
//	}
}

