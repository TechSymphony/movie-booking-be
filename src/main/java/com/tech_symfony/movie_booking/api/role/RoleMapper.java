package com.tech_symfony.movie_booking.api.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	@Mapping(target = "name", source = "name")
	@Mapping(target = "permissions", source = "permissions")
	@Mapping(target = "id", source = "id")
	RoleDto roleToRoleDTO(Role role);
}
