//package com.tech_symfony.movie_booking.api.movie.elastic;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//public interface MovieSearchRepository extends ElasticsearchRepository<MovieSearch, String> {
//	@Query("""
//		{
//			"wildcard": {
//				"name": "?0*"
//			}
//		}
//		""")
//	Page<MovieSearch> findByName(String name, Pageable pageable);
//}
