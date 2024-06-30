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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

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


//	@Test
//	public void createBillSuccessfully() {
//		// Given
//		BillDTO billDTO = new BillDTO();
//		billDTO.setShowtimeId(123);
//		billDTO.setSeatId(Arrays.asList(1, 2, 3));
//
//		Showtime showtime = new Showtime();
//		showtime.setId(123);
//		Room room = new Room();
//		room.setId(456);
//		showtime.setRoom(room);
//
//		SeatType seatType = new SeatType();
//		seatType.setId(1);
//		seatType.setName("VIP");
//		seatType.setPrice(100.0);
//		Seat seat1 = new Seat();
//		seat1.setId(1);
//		seat1.setRoom(room);
//		seat1.setType(seatType);
//
//		Seat seat2 = new Seat();
//		seat2.setId(2);
//		seat2.setRoom(room);
//		seat2.setType(seatType);
//
//		List<Seat> seatList = Arrays.asList(seat1, seat2);
//
//		User user = new User();
//		user.setId(UUID.randomUUID());
//
//		Bill bill = new Bill();
//		bill.setId(UUID.randomUUID());
//		bill.setTotal(200.0);
//
//		// When
//
//		when(billRepository.save(any(Bill.class))).thenReturn(bill);
//		when(ticketRepository.findByShowtimeAndSeat(anyInt(), anyInt())).thenReturn(new HashSet<>());
//		when(showtimeRepository.findById(anyInt())).thenReturn(Optional.of(showtime));
//		when(seatRepository.findAllById(anyList())).thenReturn(seatList);
//		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
//		when(vnpayService.doPost(any(Bill.class))).thenReturn("https://vnpay.vn");
//
//		Bill result = billService.create(billDTO, "test@example.com");
//
//		// Then
//		assertNotNull(result);
//		verify(billRepository, times(1)).save(any(Bill.class));
//	}
//
//	@Test
//	public void payBillSuccessfully() {
//		// Given
//		Bill bill = new Bill();
//		bill.setId(UUID.randomUUID());
//		bill.setTotal(100.0);
//		bill.setStatus(BillStatus.HOLDING);
//		bill.setPaymentAt(LocalDateTime.now());
//		bill.setTransactionId("123456");
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("vnp_TransactionNo", "123456");
//		Optional<Bill> optionalBill = Optional.of(bill);
//		when(billRepository.findById(any(UUID.class))).thenReturn(optionalBill);
//		when(billRepository.save(any(Bill.class))).thenReturn(bill);
//
//		when(vnpayService.verifyPay(bill)).thenReturn(jsonObject);
//
//		// When
//		Bill result = billService.pay(bill.getId());
//
//		// Then
//		assertNotNull(result);
//		assertEquals("123456", result.getTransactionId());
//		verify(billRepository, times(1)).save(any(Bill.class));
//	}
}

