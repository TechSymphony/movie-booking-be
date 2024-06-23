package com.tech_symfony.movie_booking.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/auth/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		String result = userService.register(request);
		return ResponseEntity.ok(result);
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
