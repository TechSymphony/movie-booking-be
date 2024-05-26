package com.tech_symfony.movie_booking.role;

import com.tech_symfony.movie_booking.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	@NotNull
	private String name;

	@OneToMany(
		mappedBy = "role",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<User> users;
}
