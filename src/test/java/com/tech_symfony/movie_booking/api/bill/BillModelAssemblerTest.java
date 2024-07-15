package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.model.BaseUnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


class BillModelAssemblerTest extends BaseUnitTest {

	@InjectMocks
	private BillModelAssembler billModelAssembler;
	@Mock
	private ProjectionFactory projectionFactory;
	@Mock
	private RepositoryEntityLinks entityLinks;
	private static Bill billInProgress;
	private static Bill billNotInProgress;
	private static BillInfoProjection billInfoProjection = new BillInfoProjection() {
		@Override
		public UUID getId() {
			return null;
		}

		@Override
		public Double getTotal() {
			return 0.0;
		}

		@Override
		public BillStatus getStatus() {
			return null;
		}

		@Override
		public LocalDateTime getPaymentAt() {
			return null;
		}

		@Override
		public String getTransactionId() {
			return "";
		}
	};

	@BeforeAll
	public static void setUp() {
		billInProgress = new Bill();
		billInProgress.setId(UUID.randomUUID());
		billInProgress.setStatus(BillStatus.IN_PROGRESS);
		billInProgress.setTotal(100.5); // Random total amount between 1 and 500
		billInProgress.setVnpayUrl("http://example.com/payment/" + UUID.randomUUID()); // Simulated payment URL
		billInProgress.setPaymentAt(LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(1, 30))); // Payment date within the last 30 days
		billInProgress.setTransactionId(UUID.randomUUID().toString()); // Random transaction ID
		billNotInProgress = new Bill();
		billNotInProgress.setId(UUID.randomUUID());
		billNotInProgress.setStatus(BillStatus.HOLDING);


	}


	@Test
	void toModel_ShouldIncludePayLink_ForBillsInProgress() {

		Link linkNewResource = WebMvcLinkBuilder.linkTo(Bill.class).slash(billInProgress.getId()).withSelfRel();
		Link linkCollectionResource = WebMvcLinkBuilder.linkTo(Bill.class).withRel("bills");

		when(entityLinks.linkToItemResource(Bill.class, billInProgress.getId())).thenReturn(linkNewResource);
		when(entityLinks.linkToCollectionResource(Bill.class)).thenReturn(linkCollectionResource);
		when(projectionFactory.createProjection(BillInfoProjection.class, billInProgress)).thenReturn(billInfoProjection);
		EntityModel<BillInfoProjection> billModel = billModelAssembler.toModel(billInProgress);

		assertNotNull(billModel);
		assertTrue(billModel.getLinks().contains(linkNewResource));
		assertTrue(billModel.getLinks().contains(linkCollectionResource));
		assertTrue(billModel.getLinks().hasLink("pay"));


	}

	@Test
	void toModel_ShouldNotIncludePayLink_ForBillsNotInProgress() {
		Link linkNewResource = WebMvcLinkBuilder.linkTo(Bill.class).slash(billNotInProgress.getId()).withSelfRel();
		Link linkCollectionResource = WebMvcLinkBuilder.linkTo(Bill.class).withRel("bills");

		when(entityLinks.linkToItemResource(Bill.class, billNotInProgress.getId())).thenReturn(linkNewResource);
		when(entityLinks.linkToCollectionResource(Bill.class)).thenReturn(linkCollectionResource);
		when(projectionFactory.createProjection(BillInfoProjection.class, billNotInProgress)).thenReturn(billInfoProjection);
		EntityModel<BillInfoProjection> billModel = billModelAssembler.toModel(billNotInProgress);

		assertNotNull(billModel);
		assertTrue(billModel.getLinks().contains(linkNewResource));
		assertTrue(billModel.getLinks().contains(linkCollectionResource));
		assertThat(billModel.getLink("pay")).isNotPresent();
	}
}
