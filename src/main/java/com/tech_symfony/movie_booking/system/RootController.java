
package com.tech_symfony.movie_booking.system;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tech_symfony.movie_booking.order.OrderController;

@RestController
class RootController {

	@GetMapping
	RepresentationModel<?> index() {

		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(linkTo(methodOn(OrderController.class).all()).withRel("orders"));
		return rootModel;
	}

}
