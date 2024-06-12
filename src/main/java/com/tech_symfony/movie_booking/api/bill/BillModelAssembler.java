package com.tech_symfony.movie_booking.api.bill;


import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
class BillModelAssembler implements RepresentationModelAssembler<Bill, EntityModel<Bill>> {

	private final RepositoryEntityLinks entityLinks;

	@Override
	public EntityModel<Bill> toModel(Bill bill) {
		Link linkNewResource = entityLinks.linkToItemResource(Bill.class, bill.getId());
		Link linkCollectionResource = entityLinks.linkToCollectionResource(Bill.class);

		// Unconditional links to single-item resource and aggregate root

		EntityModel<Bill> billModel = EntityModel.of(
			bill,
			linkNewResource,
			linkCollectionResource);

		// Conditional links based on state of the order

		if (bill.getStatus() == BillStatus.IN_PROGRESS) {
//			orderModel.add(linkTo(methodOn(BillController.class).cancel(bill.getId())).withRel("cancel"));
			billModel.add(linkTo(methodOn(BillController.class).pay(bill.getId())).withRel("pay"));
		}

		return billModel;
	}

}
