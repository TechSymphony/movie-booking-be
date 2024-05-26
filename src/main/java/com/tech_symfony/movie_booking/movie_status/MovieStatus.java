package com.tech_symfony.movie_booking.movie_status;

import com.tech_symfony.movie_booking.movie.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "movies_status")
public class MovieStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String slug;

	@OneToMany(
		mappedBy = "status",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Movie> movies;

}
