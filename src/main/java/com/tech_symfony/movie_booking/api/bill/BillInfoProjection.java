package com.tech_symfony.movie_booking.api.bill;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.UUID;

@Projection(name = "billProjection", types = {Bill.class})
interface BillInfoProjection {

	UUID getId();

	Double getTotal();

	BillStatus getStatus();

	LocalDateTime getPaymentAt();

	String getTransactionId();


}
