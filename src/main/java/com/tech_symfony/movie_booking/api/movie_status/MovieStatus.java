package com.tech_symfony.movie_booking.api.movie_status;

import com.tech_symfony.movie_booking.api.movie.Movie;
import com.tech_symfony.movie_booking.model.NamedEntity;
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
public class MovieStatus extends NamedEntity {

	@Transient
	private String description;

	public String getDescription() {
		return this.getName();
	}

	private String slug;

	@OneToMany(
		mappedBy = "status",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Movie> movies;

}
