package com.tech_symfony.movie_booking.api.movie.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieSearchRepository movieSearchRepository;

	Page<MovieSearch> search(Pageable pageable, Optional<String> name) {
		return movieSearchRepository.findByName(name.orElse(""), pageable);
	}
}
