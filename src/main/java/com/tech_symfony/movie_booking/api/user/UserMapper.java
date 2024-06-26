package com.tech_symfony.movie_booking.api.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "principle", source = "email")
	@Mapping(target = "credential", source = "password")
	@Mapping(target = "role", source = "user.role")
	@Mapping(target = "enabled", source = "verify")
	CustomUserDetail userToCustomerUserDetail(User user);
}
