package com.tech_symfony.movie_booking.api.room;

import com.tech_symfony.movie_booking.api.cinema.Cinema;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface RoomRepository extends BaseAuthenticatedRepository<Room, Integer> {
//	@Query("SELECT r FROM Room r " +
//		"WHERE r.cinema.id = :cinemaId")
//	List<Room> findAllByCinemaId(@Param("cinemaId") String cinemaId);
//
//	@Query("SELECT r FROM Room r " +
//		"WHERE r.cinema.id = :cinemaId AND r.name LIKE :name")
//	Page<Room> findByNameInCinema(@Param("cinemaId") String cinemaId, @Param("name") String name, Pageable pageable);
//
//
//	@Modifying
//	@Transactional
//	@Query(value = "INSERT INTO `booking_movie`.`rooms` " +
//		"(`room_id`, `available_seats`, `name`, `total_seats`, `cinema_id`, `slug`, `status_id`) VALUES " +
//		"(:roomId, :availableSeats, :name, :totalSeats, :cinemaId, :slug, 1);", nativeQuery = true)
//	void insertRoom(@Param("roomId") String roomId,
//					@Param("availableSeats") Integer availableSeats,
//					@Param("name") String name,
//					@Param("totalSeats") Integer totalSeats,
//					@Param("cinemaId") String cinemaId,
//					@Param("slug") String slug
//	);
	@Override
	List<Room> findAll();

	@Override
	Optional<Room> findById(Integer id);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'SAVE_ROOM')")
	@Override
	Room save(Room room);

	@PreAuthorize("@permissionService.hasPermission(authentication, 'DELETE_ROOM')")
	@Override
	void deleteById(Integer id);
}
