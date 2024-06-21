package com.tech_symfony.movie_booking.api.movie.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface MovieElasticService {
	Page<MovieSearch> search(Pageable pageable, Optional<String> name);
}

@Service
@RequiredArgsConstructor
class DefaultMovieElasticService implements MovieElasticService {

	private final MovieSearchRepository movieSearchRepository;

	@Override
	public Page<MovieSearch> search(Pageable pageable, Optional<String> name) {
		return movieSearchRepository.findByName(name.orElse(""), pageable);
	}
}
