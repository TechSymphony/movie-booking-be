package com.tech_symfony.movie_booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Simple JavaBean domain object representing an person.
 *
 *
 */
@MappedSuperclass
@Data
public class Person extends BaseEntity {

	@Column(name = "first_name")
	@NotBlank
	private String firstName;

	@Column(name = "last_name")
	@NotBlank
	private String lastName;

}
