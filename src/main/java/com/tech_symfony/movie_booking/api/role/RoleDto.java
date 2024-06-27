package com.tech_symfony.movie_booking.api.role;

import com.tech_symfony.movie_booking.api.role.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
	private Integer id;
	private String name;
	private Set<Permission> permissions;
}
