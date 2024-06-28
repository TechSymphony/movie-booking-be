package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.api.showtime.Showtime;
import com.tech_symfony.movie_booking.model.BaseAuthenticatedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(excerptProjection = UserInfoProjector.class)
public interface UserRepository extends BaseAuthenticatedRepository<User, UUID> {
//    @Transactional
//    @Modifying
//    @Query("UPDATE UserEntity u SET u.verify = ?1, u.role = ?2 WHERE u.email = ?3")
//    void updateVerifyAndRoleByEmail(Boolean verify, RoleEntity role, String email);

	@Override
	@PostAuthorize("returnObject.get().email == principal.username")
	Optional<User> findById(UUID uuid);

	@RestResource(exported = false)
	Optional<User> findByEmail(String email);

	@PostAuthorize("hasAuthority( 'SAVE_USER') or returnObject.get().email == principal.username")
	@Override
	User save(User user);

	@PreAuthorize("hasAuthority( 'DELETE_USER')")
	@Override
	void deleteById(UUID uuid);

	@RestResource(exported = false)
	Optional<User> findByEmailAndProviderId(String email, String providerID);
}
