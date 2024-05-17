package com.tech_symfony.movie_booking.restdocs_example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Data
public class CrudInput {

	@NotNull
	private long id;

	@NotBlank
	private String title;

	private String body;

	private List<URI> tagUris;

	@JsonCreator
	public CrudInput(@JsonProperty("id") long id, @JsonProperty("title") String title, @JsonProperty("body") String body, @JsonProperty("tags") List<URI> tagUris) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.tagUris = tagUris == null ? Collections.<URI>emptyList() : tagUris;
	}


}
