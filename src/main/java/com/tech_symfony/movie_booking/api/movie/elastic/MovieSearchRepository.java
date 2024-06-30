package com.tech_symfony.movie_booking.api.movie.elastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieSearchRepository extends ElasticsearchRepository<MovieSearch, String> {
	@Query("""
		{
			"match": {
				"name": {
					"query": "?0",
					"fuzziness" : "AUTO"
				}
			}
		}
		""")
	Page<MovieSearch> findByName(String name, Pageable pageable);
}
