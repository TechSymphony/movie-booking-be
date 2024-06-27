package com.tech_symfony.movie_booking.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@BasePathAwareController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/auth/register")
	@ResponseBody
	public String register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
	}

	@GetMapping("/auth/verify")
	public ResponseEntity<String> verify(@RequestParam(name = "t") String token) {
		userService.verifyUser(token);
		return ResponseEntity.ok("Success");
	}

	@GetMapping("/auth/unverified")
	public ResponseEntity<String> unverified() {
		return ResponseEntity.status(HttpStatus.FOUND).body("Unverified");
	}
}
