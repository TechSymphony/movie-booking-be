package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BillTest extends BaseUnitTest {

	private Bill bill;
	private Validator validator;

	@BeforeEach
	public void setUp() {
		bill = new Bill();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		bill.setUser(new User());
	}

	@Test
	public void testCreateTimeInitialization() {
		assertNull(bill.getCreateTime());
	}

	@Test
	public void testPaymentAtInitialization() {
		assertNull(bill.getPaymentAt());
	}

	@Test
	public void testChangedPointValidation() {
		bill.setChangedPoint(51);
		Set<ConstraintViolation<Bill>> violations = validator.validate(bill);
		assertFalse(violations.isEmpty());
		assertEquals("Changed point MUST be less than or equal to 18", violations.iterator().next().getMessage());

		bill.setChangedPoint(-1);
		violations = validator.validate(bill);
		assertFalse(violations.isEmpty());
		assertEquals("Changed point MUST MUST be at least 0", violations.iterator().next().getMessage());
	}

	@Test
	public void testTotalValidation() {
		bill.setTotal(-1.0);
		Set<ConstraintViolation<Bill>> violations = validator.validate(bill);
		assertFalse(violations.isEmpty());
		assertEquals("Total must be positive", violations.iterator().next().getMessage());
	}

	@Test
	public void testTransactionId() {
		String transactionId = "12345";
		bill.setTransactionId(transactionId);
		assertEquals(transactionId, bill.getTransactionId());
	}

	@Test
	public void testCancelReasonValidation() {
		bill.setCancelReason(null);
		Set<ConstraintViolation<Bill>> violations = validator.validate(bill);
		assertFalse(violations.isEmpty());
		assertEquals("Cancel reason must not be null", violations.iterator().next().getMessage());
	}

	@Test
	public void testCancelDateInitialization() {
		assertNull(bill.getCancelDate());
	}

	@Test
	public void testTicketsInitialization() {
		assertNull(bill.getTickets());
	}

	@Test
	public void testAddTicket() {
		Ticket ticket = new Ticket();
		bill.addTicket(ticket);

		assertNotNull(bill.getTickets());
		assertTrue(bill.getTickets().contains(ticket));
		assertEquals(bill, ticket.getBill());
	}

	@Test
	public void testUserValidation() {
		bill.setUser(null);
		Set<ConstraintViolation<Bill>> violations = validator.validate(bill);
		assertFalse(violations.isEmpty());
		assertEquals("User must not be null", violations.iterator().next().getMessage());
	}

	@Test
	public void testStatusInitialization() {
		assertEquals(BillStatus.IN_PROGRESS, bill.getStatus());
	}

	@Test
	public void testVnpayUrl() {
		String vnpayUrl = "http://example.com";
		bill.setVnpayUrl(vnpayUrl);
		assertEquals(vnpayUrl, bill.getVnpayUrl());
	}

	@Test
	public void testBillSuccess() {
		bill.setChangedPoint(10);
		bill.setTotal(100.50);
		bill.setTransactionId("TX123456789");
		bill.setCancelReason("Not applicable");
		bill.setCancelDate(LocalDateTime.now());

		User user = new User();
		bill.setUser(user);

		bill.setStatus(BillStatus.COMPLETED);

		Ticket ticket1 = new Ticket();
		Ticket ticket2 = new Ticket();
		bill.addTicket(ticket1);
		bill.addTicket(ticket2);

		Set<ConstraintViolation<Bill>> violations = validator.validate(bill);
		assertTrue(violations.isEmpty());

		assertEquals(10, bill.getChangedPoint());
		assertEquals(100.50, bill.getTotal());
		assertEquals("TX123456789", bill.getTransactionId());
		assertEquals("Not applicable", bill.getCancelReason());
		assertNotNull(bill.getCancelDate());
		assertEquals(user, bill.getUser());
		assertEquals(BillStatus.COMPLETED, bill.getStatus());
		assertNotNull(bill.getTickets());
		assertEquals(2, bill.getTickets().size());
		assertTrue(bill.getTickets().contains(ticket1));
		assertTrue(bill.getTickets().contains(ticket2));
	}
}

