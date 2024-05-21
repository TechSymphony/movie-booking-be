package com.tech_symfony.movie_booking.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// tag::main[]
@RestController
public class OrderController {

	private final OrderModelAssembler assembler;
	private final OrderService orderService;

	OrderController(OrderModelAssembler assembler, OrderService orderService) {

		this.assembler = assembler;
		this.orderService = orderService;
	}

	@GetMapping("/orders")
	public CollectionModel<EntityModel<Order>> all() {
		return assembler.toCollectionModel(orderService.findAllOrders());
	}

	@GetMapping("/orders/{id}")
	EntityModel<Order> one(@PathVariable Long id) {

		return assembler.toModel(orderService.getOrderById(id));
	}

	@PostMapping("/orders")
	@ResponseStatus(code = CREATED)
	ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

		Order newOrder = orderService.saveOrder(order);

		return ResponseEntity
			.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
			.body(assembler.toModel(newOrder));
	}

	@DeleteMapping("/orders/{id}/cancel")
	EntityModel<Order> cancel(@PathVariable Long id) {
		return assembler.toModel(orderService.cancelOrder(id));

	}

	@PutMapping("/orders/{id}/complete")
	EntityModel<Order> complete(@PathVariable Long id) {

		return assembler.toModel(orderService.completeOrder(id));

	}
}
