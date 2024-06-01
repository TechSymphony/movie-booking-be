package com.tech_symfony.movie_booking.api.bill;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.TimerTask;
import java.util.UUID;

@RequiredArgsConstructor
public class DeleteBillTask extends TimerTask {

	private final BillRepository billRepository;

	private final UUID billId;

	@Override
	public void run() {
		Optional<Bill> billEntity = billRepository.findById(billId);
		if (billEntity.isPresent() && billEntity.get().getStatus() == BillStatus.IN_PROGRESS) {
			billRepository.deleteById(billId);
		}
		cancel();
	}
}
