package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Entity
@Table(name = "roles")
public class Role extends NamedEntity {


//	@Column
//	@NotNull
//	private String name;

	@OneToMany(
		mappedBy = "role",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<User> users;
}
