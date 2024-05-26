package com.tech_symfony.movie_booking.movie;

import com.tech_symfony.movie_booking.movie_genre.MovieGenre;
import com.tech_symfony.movie_booking.movie_status.MovieStatus;
import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "movies")
public class Movie {

	@Id
	@GeneratedValue
	private Integer id;

	private String code;
	@NonNull
	private String name;

	@Column(name = "sub_name")
	private String subName;

	@NonNull
	private String director;

	@NonNull
	private String caster;

	@Column(name = "release_date")
	private Date releaseDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "running_time")
	private Integer runningTime;

	private String language;


	@Column(name = "number_of_ratings")
	private Integer numberOfRatings;

	@Column(name = "sum_of_ratings")
	private Integer sumOfRatings;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String poster;

	@Column(name = "horizontal_poster")
	private String horizontalPoster;

	@NonNull
	private String trailer;

	private Integer rated;

	private String producer;

	private String slug;

	//	@OneToMany(
//		mappedBy = "movie",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<ShowtimeEntity> showtimes;
//
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//	@JoinTable(
//		name = "movie_format",
//		joinColumns = @JoinColumn(name = "movie_id", nullable = false),
//		inverseJoinColumns = @JoinColumn(name = "format_id", nullable = false)
//	)
//	private Set<FormatEntity> formats;
//
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
		name = "movie_genre",
		joinColumns = @JoinColumn(name = "movie_id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "movie_genre_id", nullable = false)
	)
	private Set<MovieGenre> genres;
	//
//	@OneToMany(
//		mappedBy = "movie",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<MovieImageEntity> images;
//
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "status_id",
		nullable = false
	)
	private MovieStatus status;


}
