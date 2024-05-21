package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.model.BaseTest;
import com.tech_symfony.movie_booking.restdocs_example.CrudInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.constraints.ConstraintDescriptions;

import java.util.HashMap;
import java.util.Map;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@SpringBootTest(classes = MovieBookingApplication.class)
public class CrudExampleIntegrationTest extends BaseTest {
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	public void crudGetExample() throws Exception {

		Map<String, Object> crud = new HashMap<>();
		crud.put("id", 1L);
		crud.put("title", "Sample Model");
		crud.put("body", "cc dang iu cute 123");

		String tagLocation = this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getHeader("Location");

		crud.put("tags", singletonList(tagLocation));

		ConstraintDescriptions desc = new ConstraintDescriptions(CrudInput.class);

		this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isOk())
			.andDo(document("crud-get-example",
				resourceDetails().description("Get CRUD resource"),
				requestFields(fieldWithPath("id").description("The id of the input" + collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
					fieldWithPath("title").description("The title of the input"), fieldWithPath("body").description("The body of the input"), fieldWithPath("tags").description("An array of tag resource URIs"))));
	}

	//
	@Test
	public void crudCreateExample() throws Exception {
		Map<String, Object> crud = new HashMap<>();
		crud.put("id", 2L);
		crud.put("title", "Sample Model");
		crud.put("body", "cc dang iu cute 123");

		String tagLocation = this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getHeader("Location");

		crud.put("tags", singletonList(tagLocation));

		this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isCreated())
			.andDo(document("crud-create-example",
					resourceDetails().description("Create CRUD resource"),
					requestFields(fieldWithPath("id").description("The id of the input"),
						fieldWithPath("title").description("The title of the input"),
						fieldWithPath("body").description("The body of the input"),
						fieldWithPath("tags").description("An array of tag resource URIs"))
				)
			);
	}

	//
	@Test
	public void crudDeleteExample() throws Exception {
		this.mockMvc.perform(delete("/crud/{id}", 10))
			.andExpect(status().isOk())
			.andDo(document("crud-delete-example", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
	}


}
