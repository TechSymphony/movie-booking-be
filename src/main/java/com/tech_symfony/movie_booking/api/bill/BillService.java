package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.bill.vnpay.VnpayService;
import com.tech_symfony.movie_booking.api.seat.Seat;
import com.tech_symfony.movie_booking.api.seat.SeatRepository;
import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.api.showtime.ShowtimeRepository;
import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.ticket.TicketRepository;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.api.user.UserRepository;
import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

public interface BillService {

	Bill create(BillRequestDTO dataRaw, String username);

	Bill pay(UUID billID, String username);

	Bill updateStatus(UUID billId);

}

@Service
@RequiredArgsConstructor
class DefaultBillService implements BillService {

	//15minute
	private Integer payExpiration = 30 * 60 * 1000; // minute * second * millisecond

	private final BillRepository billRepository;

	private final ShowtimeRepository showtimeRepository;

	private final TicketRepository ticketRepository;

	private final SeatRepository seatRepository;

	private final UserRepository userRepository;


	private final VnpayService vnpayService;


	private Boolean checkSeat(Showtime showtime, List<Seat> seatList) {

		Set<Ticket> t = ticketRepository.findByShowtimeAndSeatIn(showtime, seatList);
		if (t.size() > 0) {
			throw new ForbidenMethodControllerException("Seat already reserved");
		}
		return true;
	}

	private Double getPriceOfSeat(Seat seat) {
		return seat.getType().getPrice();
	}


	@Override
	public Bill create(BillRequestDTO billRequestDTO, String username) {
		Showtime showtime = showtimeRepository.findById(billRequestDTO.getShowtimeId())
			.orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));
		List<Seat> seatList = seatRepository
			.findByIdInAndRoomId(billRequestDTO.getSeatId(), showtime.getRoom().getId())
			.orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

		checkSeat(showtime, seatList);


		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Conflict"));

		Bill bill = new Bill();
		bill.setTotal(seatList.stream().mapToDouble(this::getPriceOfSeat).sum());
		bill.setUser(user);


		seatList.stream()
			.forEach(seat ->
				bill.addTicket(
					Ticket.builder()
						.seat(seat)
						.showtime(showtime)
						.build()
				)
			);
		;
		Timer timer = new Timer();


		Bill savedBill = billRepository.save(bill);
		savedBill.setVnpayUrl(vnpayService.doPost(savedBill));
		timer.schedule(new DeleteBillTask(billRepository, savedBill.getId()), payExpiration);
		return savedBill;
	}


	@Override
	public Bill pay(UUID billID, String username) {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("Conflict"));
		Bill bill = billRepository.findByIdAndUser(billID, user)
			.orElseThrow(() -> new ResourceNotFoundException("Bill is not exits"));
		JSONObject jsonObject = vnpayService.verifyPay(bill);


		bill.setStatus(BillStatus.HOLDING);
		bill.setPaymentAt(LocalDateTime.now());
		bill.setTransactionId(jsonObject.getString("vnp_TransactionNo"));
		return billRepository.save(bill);

	}

	@PreAuthorize("hasAuthority('SAVE_BILL')")
	public Bill updateStatus(UUID billId) {

		Bill bill = billRepository.findById(billId)
			.orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
		if (bill.getStatus() != BillStatus.HOLDING) {
			throw new ForbidenMethodControllerException("Bill is not holding");
		}
		bill.setStatus(BillStatus.COMPLETED);
		return billRepository.save(bill);
	}


}
