package com.tech_symfony.movie_booking.api.movie;

import com.tech_symfony.movie_booking.indexing.MovieIndexRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieIndexRepository movieIndexRepository;

	Page<Movie> search(Pageable pageable, Optional<String> name) {
		return movieIndexRepository.findBySubName(name.orElse(""), pageable);
	}
}
