package com.tech_symfony.movie_booking.api.movie.elastic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultMovieElasticServiceTest {

	@Mock
	private ElasticsearchOperations operations;

	@InjectMocks
	private DefaultMovieElasticService movieElasticService;

	private Pageable pageable;
	private Optional<String> name;

	@BeforeEach
	void setUp() {
		pageable = PageRequest.of(0, 10);
		name = Optional.of("test");
	}

	@Test
	void testSearchWithName() {
		SearchHits<MovieSearch> searchHits = mock(SearchHits.class);

		when(operations.search(any(Query.class), eq(MovieSearch.class))).thenReturn(searchHits);
		when(searchHits.getSearchHits()).thenReturn(List.of());
		when(searchHits.getTotalHits()).thenReturn(0L);

		Page<MovieSearch> result = movieElasticService.search(pageable, name);

		verify(operations, times(1)).search(any(Query.class), eq(MovieSearch.class));
		assertEquals(0, result.getTotalElements());
	}
}
