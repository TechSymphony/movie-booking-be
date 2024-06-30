package com.tech_symfony.movie_booking.api.user.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@Email(message = "Invalid email")
	@NotBlank(message = "Email MUST not be blank")
	private String email;

	@NotBlank(message = "Name MUST not be blank")
	private String fullName;

	@NotBlank(message = "Password MUST not be blank")
	private String password;
}
