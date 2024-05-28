
package com.tech_symfony.movie_booking.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects
 * needing this property.
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity implements Serializable {

	@Id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public boolean isNew() {
		return this.id == null;
	}

}
