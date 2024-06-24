package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Integer id;

	private String name;

	private String decription;
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles;
}
