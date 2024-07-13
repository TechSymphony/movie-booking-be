
package com.tech_symfony.movie_booking.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller used to showcase what happens when an exception is thrown
 */
@RestController
class CrashController {

	@GetMapping("/oups")
	public ResponseEntity<String> triggerException() {
		throw new RuntimeException(
			"Expected: controller used to showcase what " + "happens when an exception is thrown");
	}

}
