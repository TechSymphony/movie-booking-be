package com.tech_symfony.movie_booking.api.bill;


import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
class BillModelAssembler implements RepresentationModelAssembler<Bill, EntityModel<BillInfoProjection>> {

	private final RepositoryEntityLinks entityLinks;
	private final ProjectionFactory projectionFactory;

	@Override
	public EntityModel<BillInfoProjection> toModel(Bill bill) {
		Link linkNewResource = entityLinks.linkToItemResource(Bill.class, bill.getId());
		Link linkCollectionResource = entityLinks.linkToCollectionResource(Bill.class);

		// Unconditional links to single-item resource and aggregate root
		BillInfoProjection projection = projectionFactory.createProjection(BillInfoProjection.class, bill);

		EntityModel<BillInfoProjection> billModel = EntityModel.of(
			projection,
			linkNewResource,
			linkCollectionResource);

		// Conditional links based on state of the order

		if (bill.getStatus() == BillStatus.IN_PROGRESS) {
			billModel.add(linkTo(methodOn(BillController.class).pay(bill.getId())).withRel("pay"));
		}
		return billModel;
	}

}
