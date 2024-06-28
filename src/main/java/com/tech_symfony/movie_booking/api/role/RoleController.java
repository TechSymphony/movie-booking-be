package com.tech_symfony.movie_booking.api.role;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@BasePathAwareController
@RequiredArgsConstructor
@ResponseBody
public class RoleController {
	private final RoleService roleService;
	private final RoleModelAssembler assembler;

	@PreAuthorize("hasAuthority( 'READ_ROLE')")
	@GetMapping("/roles")
	public CollectionModel<EntityModel<RoleDto>> getAllRoles() {
		return assembler.toCollectionModel(roleService.findAll());
	}

	@GetMapping("/roles/{id}")
	@PreAuthorize("hasAuthority( 'READ_ROLE')")
	public EntityModel<RoleDto> getById(@PathVariable Integer id) {
		return assembler.toModel(roleService.findById(id));
	}

	@PreAuthorize("hasAuthority( 'SAVE_ROLE')")
	@PutMapping("/roles/{id}")
	public EntityModel<RoleDto> updateRole(@PathVariable Integer id, @RequestBody Role role) {
		return assembler.toModel(roleService.update(id, role));
	}

	@PreAuthorize("hasAuthority( 'SAVE_ROLE')")
	@PostMapping("/roles")
	public ResponseEntity<EntityModel<RoleDto>> createRole(@RequestBody Role role) {
		Role newRole = roleService.save(role);
		return ResponseEntity
			.created(linkTo(methodOn(RoleController.class).getById(newRole.getId())).toUri())
			.body(assembler.toModel(newRole));
	}

	@PreAuthorize("hasAuthority( 'DELETE_ROLE')")
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
		return ResponseEntity.ok().body(roleService.delete(id));
	}
}
