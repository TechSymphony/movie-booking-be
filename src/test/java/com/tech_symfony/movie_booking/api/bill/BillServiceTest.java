package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.bill.vnpay.VnpayService;
import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.seat.SeatRepository;
import com.tech_symfony.movie_booking.api.seat_type.SeatType;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.ticket.TicketRepository;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.api.user.UserRepository;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

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

	private static UUID existingBillId;
	private static Bill holdingBill;
	private static Bill completedBill;

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

		existingBillId = UUID.randomUUID();
		holdingBill = new Bill();
		holdingBill.setId(existingBillId);
		holdingBill.setStatus(BillStatus.HOLDING);

		completedBill = new Bill();
		completedBill.setId(existingBillId);
		completedBill.setStatus(BillStatus.COMPLETED);

	}

	@Test
	void create_ShouldThrowResourceNotFoundException_WhenShowtimeDoesNotExist() {


		when(showtimeRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> billService.create(billRequestDTO, user.getEmail()));
	}

	@Test
	void create_ShouldThrowResourceNotFoundException_WhenSeatsDoNotExist() {


		when(showtimeRepository.findById(showtime.getId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(anyList(), any(Integer.class))).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> billService.create(billRequestDTO, user.getEmail()));
	}

	@Test
	void create_ShouldThrowResourceNotFoundException_WhenSeatsAlreadyReserved() {


		when(showtimeRepository.findById(billRequestDTO.getShowtimeId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId())).thenReturn(Optional.of(seatList));
		when(ticketRepository.findByShowtimeAndSeatIn(showtime, seatList)).thenReturn(Set.of(new Ticket()));

		Exception exception = assertThrows(ForbidenMethodControllerException.class, () -> billService.create(billRequestDTO, user.getEmail()));
		assertEquals("Seat already reserved", exception.getMessage());
		verify(showtimeRepository, times(1)).findById(billRequestDTO.getShowtimeId());
		verify(seatRepository, times(1)).findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId());
		verify(ticketRepository, times(1)).findByShowtimeAndSeatIn(showtime, seatList);

	}

	@Test
	void create_ShouldThrowException_WhenUserNotFound() {


		when(showtimeRepository.findById(billRequestDTO.getShowtimeId())).thenReturn(Optional.of(showtime));
		when(seatRepository.findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId())).thenReturn(Optional.of(seatList));
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

		Exception exception = assertThrows(UsernameNotFoundException.class, () -> billService.create(billRequestDTO, user.getEmail()));

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
	void pay_ShouldThrowException_WhenUserNotFound() {
		UUID billID = UUID.randomUUID();
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());


		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
			billService.pay(billID, user.getEmail());
		});

		assertEquals("Conflict", exception.getMessage());
	}

	@Test
	void pay_ShouldThrowException_WhenBillNotFound() {
		UUID billID = UUID.randomUUID();
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(billRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.empty());

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

		when(vnpayService.verifyPay(bill)).thenReturn(jsonObject);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(billRepository.findByIdAndUser(any(UUID.class), any(User.class))).thenReturn(Optional.of(bill));
		when(billRepository.save(bill)).thenAnswer(invocation -> invocation.getArgument(0));

		Bill updatedBill = billService.pay(billID, user.getEmail());

		assertNotNull(updatedBill);
		assertEquals(BillStatus.HOLDING, updatedBill.getStatus());
		assertNotNull(updatedBill.getPaymentAt());
		assertEquals("12345", updatedBill.getTransactionId());
		verify(billRepository, times(1)).save(bill);
	}

	@Test
	@WithMockUser(authorities = "SAVE_BILL")
	public void testUpdateStatus_success() {
		UUID billId = UUID.randomUUID();
		Bill bill = new Bill();
		bill.setStatus(BillStatus.HOLDING);

		when(billRepository.findById(billId)).thenReturn(Optional.of(bill));
		when(billRepository.save(bill)).thenReturn(bill);

		Bill updatedBill = billService.updateStatus(billId);

		assertNotNull(updatedBill);
		assertEquals(BillStatus.COMPLETED, updatedBill.getStatus());
		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(1)).save(bill);
	}

	@Test
	@WithMockUser(authorities = "SAVE_BILL")
	public void testUpdateStatus_billNotFound() {
		UUID billId = UUID.randomUUID();

		when(billRepository.findById(billId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			billService.updateStatus(billId);
		});

		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(0)).save(any(Bill.class));
	}

	@Test
	@WithMockUser(authorities = "SAVE_BILL")
	public void testUpdateStatus_billNotHolding() {
		UUID billId = UUID.randomUUID();
		Bill bill = new Bill();
		bill.setStatus(BillStatus.IN_PROGRESS);

		when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

		assertThrows(ForbidenMethodControllerException.class, () -> {
			billService.updateStatus(billId);
		});

		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(0)).save(any(Bill.class));
	}

//	@Test
//	public void testUpdateStatus_unauthorized() {
//		UUID billId = UUID.randomUUID();
//
//		assertThrows(AccessDeniedException.class, () -> {
//			billService.updateStatus(billId);
//		});
//
//		verify(billRepository, times(0)).findById(any(UUID.class));
//		verify(billRepository, times(0)).save(any(Bill.class));
//	}
}

