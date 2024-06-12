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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

public interface BillService {

	Bill create(BillDTO dataRaw, String username);

	Bill pay(UUID billID);


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


	private void checkSeat(Showtime showtime, List<Seat> seatList) {
		seatList.forEach(seat -> {
			if (!seat.getRoom().getId().equals(showtime.getRoom().getId())) {
				throw new ForbidenMethodControllerException("Data not found");
			}
			Set<Ticket> t = ticketRepository.findByShowtimeAndSeat(showtime.getId(), seat.getId());
			if (!t.isEmpty()) {
				throw new ForbidenMethodControllerException("Seat already reserved");
			}
		});
	}

	private Double getPriceOfSeat(Seat seat) {
		return seat.getType().getPrice();
	}


	@Override
	public Bill create(BillDTO billDTO, String username) {
		Showtime showtime = showtimeRepository.findById(billDTO.getShowtimeId())
			.orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));
		List<Seat> seatList = seatRepository.findAllById(billDTO.getSeatId());

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

//		bill.setId(UUID.fromString("e772c5cc-982d-45a2-a3d8-0e0a7e735fdf"));
//		return bill;
		Bill savedBill = billRepository.save(bill);
		savedBill.setVnpayUrl(vnpayService.doPost(savedBill));
		timer.schedule(new DeleteBillTask(billRepository, savedBill.getId()), payExpiration);
		return savedBill;
	}

	@Override
	public Bill pay(UUID billID) {
		Bill bill = billRepository.findById(billID)
			.orElseThrow(() -> new ResourceNotFoundException("Bill is not exits"));
		JSONObject jsonObject = vnpayService.verifyPay(bill);


		bill.setStatus(BillStatus.HOLDING);
		bill.setPaymentAt(LocalDateTime.now());
		bill.setTransactionId(jsonObject.getString("vnp_TransactionNo"));
		return billRepository.save(bill);
//		return bill;

	}


}