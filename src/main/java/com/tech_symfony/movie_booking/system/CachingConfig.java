
package com.tech_symfony.movie_booking.system;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
	// Mặc định, Spring Boot sử dụng SimpleCacheManager với ConcurrentHashMapCache

}
