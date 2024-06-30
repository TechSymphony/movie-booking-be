package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.api.role.*;
import com.tech_symfony.movie_booking.api.role.DTO.RoleDto;
import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;


import java.util.Collections;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MovieBookingApplication.class)
public class RoleIT extends BaseIntegrationTest {
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoleService roleService;

	@MockBean
	private RoleModelAssembler roleModelAssembler;



	@Test
	public void getById() throws Exception {
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_ADMIN");
		role.setPermissions(Collections.emptySet());
		role.setUsers(Collections.emptySet());

		given(roleService.findById(anyInt())).willReturn(role);
		given(roleModelAssembler.toModel(any())).willReturn(EntityModel.of(
				new RoleDto(role.getId(), role.getName(), role.getUsers(), role.getPermissions()))
			.add(linkTo(methodOn(RoleController.class).getById(role.getId())).withSelfRel()));

		this.mockMvc.perform(get("/roles/{id}", 1)
				.accept(MediaTypes.HAL_JSON))
			.andExpect(status().isOk())
			.andDo(result -> {
				System.out.println("Response JSON: " + result.getResponse().getContentAsString());
			})
			.andDo(document("roles-get-by-id",
				pathParameters(
					parameterWithName("id").description("The ID of the role")
				),
				responseFields(
					fieldWithPath("id").description("The ID of the role"),
					fieldWithPath("name").description("The name of the role"),
					fieldWithPath("permissions").description("The permissions of the role"),
					fieldWithPath("users").description("The users associated with the role"),
					subsectionWithPath("_links").description("Links to other resources")
				)
			));
	}


	@Test
	public void createRole() throws Exception {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		role.setPermissions(Collections.emptySet());
		role.setUsers(Collections.emptySet());

		Role createRole = new Role();
		createRole.setId(1);
		createRole.setName("ROLE_ADMIN");
		createRole.setPermissions(Collections.emptySet());
		createRole.setUsers(Collections.emptySet());

		given(roleService.save(any(Role.class))).willReturn(createRole);
		given(roleModelAssembler.toModel(any())).willReturn(EntityModel.of(
				new RoleDto(role.getId(), role.getName(), role.getUsers(), role.getPermissions()))
			.add(linkTo(methodOn(RoleController.class).getById(role.getId())).withSelfRel()));

		this.mockMvc.perform(post("/roles")
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(role)))
			.andExpect(status().isCreated())
			.andDo(document("roles-create",
				requestFields(
					fieldWithPath("id").description("The ID of the role").optional(),
					fieldWithPath("name").description("The name of the role"),
					fieldWithPath("permissions").description("The permissions of the role").optional(),
					fieldWithPath("users").description("The users associated with the role").optional()
				)
			));
	}
//
	@Test
	public void updateRole() throws Exception {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		role.setPermissions(Collections.emptySet());
		role.setUsers(Collections.emptySet());

		Role createRole = new Role();
		createRole.setId(1);
		createRole.setName("ROLE_ADMIN");
		createRole.setPermissions(Collections.emptySet());
		createRole.setUsers(Collections.emptySet());

		given(roleService.update(any(Integer.class), any(Role.class))).willReturn(createRole);
		given(roleModelAssembler.toModel(any())).willReturn(EntityModel.of(
				new RoleDto(role.getId(), role.getName(), role.getUsers(), role.getPermissions()))
			.add(linkTo(methodOn(RoleController.class).getById(role.getId())).withSelfRel()));

		this.mockMvc.perform(put("/roles/{id}", 1)
				.contentType(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(role)))
			.andExpect(status().isOk())
			.andDo(document("roles-update",
				requestFields(
					fieldWithPath("id").description("The ID of the role").optional(),
					fieldWithPath("name").description("The name of the role"),
					fieldWithPath("permissions").description("The permissions of the role").optional(),
					fieldWithPath("users").description("The users associated with the role").optional() // Ensure users is documented
				))
			);
	}
//
	@Test
	public void deleteRole() throws Exception {
		given(roleService.delete(anyInt())).willReturn(true);

		this.mockMvc.perform(delete("/roles/{id}", 1))
			.andExpect(status().isOk())
			.andDo(document("roles-delete",
				pathParameters(
					parameterWithName("id").description("The ID of the role")
				)
			));
	}

}
