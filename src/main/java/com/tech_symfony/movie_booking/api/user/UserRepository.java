package com.tech_symfony.movie_booking.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID>, JpaRepository<User, UUID> {
//    @Transactional
//    @Modifying
//    @Query("UPDATE UserEntity u SET u.verify = ?1, u.role = ?2 WHERE u.email = ?3")
//    void updateVerifyAndRoleByEmail(Boolean verify, RoleEntity role, String email);

	Optional<User> findByEmail(String email);

	Optional<User> findByFullName(String email);

	void deleteByEmail(String email);
}
