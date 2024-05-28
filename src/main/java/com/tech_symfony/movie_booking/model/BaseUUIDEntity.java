package com.tech_symfony.movie_booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Setter
@Getter
public abstract class BaseUUIDEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;


}
