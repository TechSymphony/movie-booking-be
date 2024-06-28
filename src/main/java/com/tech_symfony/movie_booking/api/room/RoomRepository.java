package com.tech_symfony.movie_booking.api.room;

import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface RoomRepository extends BaseAuthenticatedRepository<Room, Integer> {

	@PreAuthorize("hasAuthority( 'SAVE_ROOM')")
	@Override
	Room save(Room room);

	@PreAuthorize("hasAuthority( 'DELETE_ROOM')")
	@Override
	void deleteById(Integer id);
}
