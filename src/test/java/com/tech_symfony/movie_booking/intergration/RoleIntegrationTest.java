package com.tech_symfony.movie_booking.intergration;

import com.tech_symfony.movie_booking.model.BaseTest;
import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.api.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser
public class RoleIntegrationTest extends BaseTest {

	@Autowired
	private RoleRepository roleRepository;

	private void createRole(String name) {
		Role role = new Role();
		role.setName(name);

		this.roleRepository.save(role);
	}


	@Test

	public void getRoles() throws Exception {
		this.roleRepository.deleteAll();
//		createRole("ROLE_USER");
//		createRole("ROLE_ADMIN");
		createRole("ROLE_SELLER");


		this.mockMvc.perform(get("/roles"))
			.andExpect(status().isOk())
			.andDo(document("roles-list",
				resourceDetails().description("Get all Roles"),
				links(
					linkWithRel("self").description("Canonical link for this resource"),
					linkWithRel("profile").description("Canonical link for this resource")

//					linkWithRel("user").description("The ALPS user for this resource")
				),

				responseFields(
					subsectionWithPath("_embedded.roles").description("An array of <<resources_role, Role resources>>"),
					subsectionWithPath("_links").description("Links to other resources"),
					subsectionWithPath("page.size").description("The size of the page"),
					subsectionWithPath("page.totalElements").description("The total number of elements"),
					subsectionWithPath("page.totalPages").description("The total number of pages"),
					subsectionWithPath("page.number").description("The current page number")
				)
			));
	}
//
//	@Test
//	public void createRole() throws Exception {
//		Map<String, Object> role = new HashMap<>();
//		role.put("name", "ROLE_USER");
//
//		this.mockMvc.perform(post("/roles")
//				.contentType(MediaTypes.HAL_JSON)
//				.content(objectMapper.writeValueAsString(role)))
//			.andExpect(status().isCreated())
//			.andDo(document("create-role",
//				resourceDetails().description("Create a new Role"),
//				requestFields(
//					fieldWithPath("name").description("The name of the role")
//				),
//				responseFields(
//					fieldWithPath("id").description("The id of the created role"),
//					fieldWithPath("name").description("The name of the created role"),
//					fieldWithPath("users").description("The users associated with the created role")
//				)
//			));
//	}
//
//	@Test
//	public void getRoleById() throws Exception {
//		this.mockMvc.perform(get("/roles/{id}", 1))
//			.andExpect(status().isOk())
//			.andDo(document("get-role-by-id",
//				resourceDetails().description("Get Role by id"),
//				pathParameters(
//					parameterWithName("id").description("The id of the role to retrieve")
//				),
//				responseFields(
//					fieldWithPath("id").description("The id of the role"),
//					fieldWithPath("name").description("The name of the role"),
//					fieldWithPath("users").description("The users associated with the role")
//				)
//			));
//	}
//
//
//	@Test
//	public void updateRole() throws Exception {
//		Map<String, Object> role = new HashMap<>();
//		role.put("name", "ROLE_ADMIN");
//
//		this.mockMvc.perform(put("/roles/{id}", 1)
//				.contentType(MediaTypes.HAL_JSON)
//				.content(objectMapper.writeValueAsString(role)))
//			.andExpect(status().isOk())
//			.andDo(document("update-role",
//				resourceDetails().description("Update an existing Role"),
//				pathParameters(
//					parameterWithName("id").description("The id of the role to update")
//				),
//				requestFields(
//					fieldWithPath("name").description("The new name of the role")
//				),
//				responseFields(
//					fieldWithPath("id").description("The id of the updated role"),
//					fieldWithPath("name").description("The name of the updated role"),
//					fieldWithPath("users").description("The users associated with the updated role")
//				)
//			));
//	}
//
//	@Test
//	public void deleteRole() throws Exception {
//		this.mockMvc.perform(delete("/roles/{id}", 1))
//			.andExpect(status().isNoContent())
//			.andDo(document("delete-role",
//				resourceDetails().description("Delete a Role"),
//				pathParameters(
//					parameterWithName("id").description("The id of the role to delete")
//				)
//			));
//	}

}
