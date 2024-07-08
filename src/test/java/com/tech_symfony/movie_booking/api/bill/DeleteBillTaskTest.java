package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DeleteBillTaskTest extends BaseUnitTest {
	@Mock
	private BillRepository billRepository;

	private UUID billId;
	private DeleteBillTask deleteBillTask;

	@BeforeEach
	public void setUp() {
		billId = UUID.randomUUID();
		deleteBillTask = new DeleteBillTask(billRepository, billId);
	}

	@Test
	public void testRun_billInProgress() {
		Bill bill = new Bill();
		bill.setStatus(BillStatus.IN_PROGRESS);

		when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

		deleteBillTask.run();

		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(1)).deleteById(billId);
	}

	@Test
	public void testRun_billNotInProgress() {
		Bill bill = new Bill();
		bill.setStatus(BillStatus.COMPLETED);

		when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

		deleteBillTask.run();

		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(0)).deleteById(billId);
	}

	@Test
	public void testRun_billNotFound() {
		when(billRepository.findById(billId)).thenReturn(Optional.empty());

		deleteBillTask.run();

		verify(billRepository, times(1)).findById(billId);
		verify(billRepository, times(0)).deleteById(billId);
	}
}
