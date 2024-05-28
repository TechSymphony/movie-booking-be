package com.tech_symfony.movie_booking.api.movie;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@BasePathAwareController
//@RequestMapping(value = "public/movies")
public class PublicMovieController {
	// Let's specify the #produces type as text/plain (rather than the Spring Data REST JSON default)
//	@GetMapping(value = "/index")
//	public @ResponseBody ResponseEntity<String> index() {
//		// Get project by id from repository and map to string.
//		return ResponseEntity.ok("A String!");
//	}
}
