package com.tech_symfony.movie_booking.api.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.role.permission.Permission;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends NamedEntity {

	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY
	)
	@JsonIgnore
	private Set<User> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_permission",
		joinColumns = @JoinColumn(name = "role_id"),
		inverseJoinColumns = @JoinColumn(name = "permission_id")
	)
	@JsonIgnore
	private Set<Permission> permissions;

}
