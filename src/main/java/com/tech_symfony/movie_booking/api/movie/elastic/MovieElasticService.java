package com.tech_symfony.movie_booking.api.movie.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface MovieElasticService {
	Page<MovieSearch> search(Pageable pageable, Optional<String> name);
}

@Service
@RequiredArgsConstructor
class DefaultMovieElasticService implements MovieElasticService {

//	private final MovieSearchRepository movieSearchRepository;

	private final ElasticsearchOperations operations;

	@Override
	public Page<MovieSearch> search(Pageable pageable, Optional<String> name) {
		Query query = new StringQuery("{" +
			"	\"bool\": {" +
			"		\"should\": [" +
			"			{" +
			"				\"match\": {" +
			"	      			\"name\": {" +
			"	        			\"query\": \""+ name.orElse("") +"\"," +
			"	        			\"fuzziness\": \"AUTO\"" +
			"					}" +
			"	 			}" +
			"			}, " +
			"			{" +
			"				\"wildcard\": {" +
			"					\"name\": {" +
			"						\"value\": \""+ name.orElse("") + "?*\"" +
			"					}" +
			"				}" +
			"			}" +
			"		]" +
			"	}" +
			"}", pageable);
		SearchPage<MovieSearch> movies = SearchHitSupport.searchPageFor(
			operations.search(query, MovieSearch.class),
			pageable
		);
		return (Page<MovieSearch>) SearchHitSupport.unwrapSearchHits(movies);
	}
}
