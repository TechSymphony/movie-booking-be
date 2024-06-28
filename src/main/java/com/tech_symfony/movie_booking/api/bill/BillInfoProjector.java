package com.tech_symfony.movie_booking.api.bill;

import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "billProjection", types = {Bill.class})
interface BillInfoProjector {

	UUID getId();

	Double getTotal();

	BillStatus getStatus();

	String getPaymentAt();

	String getTransactionId();


}
