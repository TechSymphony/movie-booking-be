package com.tech_symfony.movie_booking.api.movie_genre;

import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Setter
@Getter

@Entity
@Table(name = "movie_genres")
public class MovieGenre extends NamedEntity {


	@ManyToMany(mappedBy = "genres")
	private Set<Movie> movies;

}
