package com.tech_symfony.movie_booking.api.movie_genre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;


@Setter
@Getter

@Entity
@Table(name = "movie_genres")
public class MovieGenre extends NamedEntity {


	@ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
	@RestResource(exported = false)
	@JsonIgnore
	private Set<Movie> movies;

}
