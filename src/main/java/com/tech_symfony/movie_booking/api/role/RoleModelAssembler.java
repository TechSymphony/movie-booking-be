package com.tech_symfony.movie_booking.api.role;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<RoleDto>> {
	@Override
	public EntityModel<RoleDto> toModel(Role role) {

		RoleDto roleDto = RoleMapper.INSTANCE.roleToRoleDTO(role);
		EntityModel<RoleDto> roleModel = EntityModel.of(roleDto,
			linkTo(methodOn(RoleController.class).getById(role.getId())).withSelfRel(),
			linkTo(methodOn(RoleController.class).getAllRoles()).withRel("roles"));
		return roleModel;
	}
}
