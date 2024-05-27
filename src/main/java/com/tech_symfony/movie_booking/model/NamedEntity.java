
package com.tech_symfony.movie_booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Simple JavaBean domain object adds a name property to
 * <code>BaseEntity</code>. Used as
 * a base class for objects needing these properties.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

	@Column
	@NotNull
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
//
//	@Override
//	public String toString() {
//		return this.getName();
//	}

}
