package com.tech_symfony.movie_booking.indexing;

import com.tech_symfony.movie_booking.api.movie.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieIndexRepository extends ElasticsearchRepository<Movie, Integer> {
	Page<Movie> findBySubName(String subName, Pageable pageable);
}
