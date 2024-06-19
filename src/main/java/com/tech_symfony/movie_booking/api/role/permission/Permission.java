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
public class Permission extends NamedEntity {
	private String decription;
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles;
}
