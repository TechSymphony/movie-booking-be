package com.tech_symfony.movie_booking.movie_genre;

import com.tech_symfony.movie_booking.movie.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movie_genres")
public class MovieGenre {

	@Id
	@GeneratedValue
	private Integer id;

	@NonNull
	private String name;

	@ManyToMany(mappedBy = "genres")
	private Set<Movie> movies;

}
