package com.tech_symfony.movie_booking.api.user;

import jakarta.validation.Valid;
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
	public String register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
	}

	@GetMapping("/auth/verify")
	public String verify(@RequestParam(name = "t") String token) {
		userService.verifyUser(token);
		return "Success";
	}

	@GetMapping("/auth/unverified")
	public ResponseEntity<String> unverified() {
		return ResponseEntity.status(HttpStatus.FOUND).body("Unverified");
	}
}
