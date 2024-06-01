package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "roles")
@Setter
@Getter
public class Role extends NamedEntity {

	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY)

	private Set<User> users;
}
