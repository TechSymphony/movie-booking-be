package com.tech_symfony.movie_booking.api.role;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@BasePathAwareController(path = "/roles")
@RequiredArgsConstructor
public class RoleController {
	private final RoleService roleService;
	private final RoleModelAssembler assembler;

	@PreAuthorize("hasAuthority( 'READ_ROLE')")
	@GetMapping
	public CollectionModel<EntityModel<RoleDto>> getAllRoles() {
		return assembler.toCollectionModel(roleService.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority( 'READ_ROLE')")
	public EntityModel<RoleDto> getById(@PathVariable Integer id) {
		return assembler.toModel(roleService.findById(id));
	}

	@PreAuthorize("hasAuthority( 'UPDATE_ROLE')")
	@PutMapping("/{id}")
	public EntityModel<RoleDto> updateRole(@PathVariable Integer id, @RequestBody Role role) {
		return assembler.toModel(roleService.update(id, role));
	}

	@PreAuthorize("hasAuthority( 'CREATE_ROLE')")
	@PostMapping
	public ResponseEntity<EntityModel<RoleDto>> createRole(@RequestBody Role role) {
		Role newRole = roleService.save(role);
		return ResponseEntity
			.created(linkTo(methodOn(RoleController.class).getById(newRole.getId())).toUri())
			.body(assembler.toModel(newRole));
	}

	@PreAuthorize("hasAuthority( 'DELETE_ROLE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
		return ResponseEntity.ok().body(roleService.delete(id));
	}
}
