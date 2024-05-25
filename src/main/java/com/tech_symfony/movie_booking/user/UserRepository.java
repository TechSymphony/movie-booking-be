package com.tech_symfony.movie_booking.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    @Transactional
//    @Modifying
//    @Query("UPDATE UserEntity u SET u.verify = ?1, u.role = ?2 WHERE u.email = ?3")
//    void updateVerifyAndRoleByEmail(Boolean verify, RoleEntity role, String email);

	Optional<User> findByEmail(String email);

	Optional<User> findByFullName(String email);

	void deleteByEmail(String email);
}
