package com.tech_symfony.movie_booking.api.movie.elastic;

import com.tech_symfony.movie_booking.api.movie.Movie;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "movies")
public class MovieSearch extends Movie {
}
