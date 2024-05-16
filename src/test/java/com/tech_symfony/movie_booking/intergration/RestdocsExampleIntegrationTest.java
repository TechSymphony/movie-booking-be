package com.tech_symfony.movie_booking.intergration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_symfony.movie_booking.MovieBookingApplication;
import com.tech_symfony.movie_booking.model.BaseTest;
import com.tech_symfony.movie_booking.restdocs_example.CrudInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@SpringBootTest(classes = MovieBookingApplication.class)
public class RestdocsExampleIntegrationTest extends BaseTest {
	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
			.build();
	}

	@Test
	public void indexExample() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(document("index-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), links(linkWithRel("orders").description("The CRUD resource")), responseFields(subsectionWithPath("_links").description("Links to other resources")),
				responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
	}

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
			.andDo(document("crud-get-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), requestFields(fieldWithPath("id").description("The id of the input" + collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
				fieldWithPath("title").description("The title of the input"), fieldWithPath("body").description("The body of the input"), fieldWithPath("tags").description("An array of tag resource URIs"))));
	}

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
			.andDo(document("crud-create-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), requestFields(fieldWithPath("id").description("The id of the input"), fieldWithPath("title").description("The title of the input"),
				fieldWithPath("body").description("The body of the input"), fieldWithPath("tags").description("An array of tag resource URIs"))));
	}

	@Test
	public void crudDeleteExample() throws Exception {
		this.mockMvc.perform(delete("/crud/{id}", 10))
			.andExpect(status().isOk())
			.andDo(document("crud-delete-example", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
	}

	@Test
	public void crudPatchExample() throws Exception {

		Map<String, String> tag = new HashMap<>();
		tag.put("name", "PATCH");

		String tagLocation = this.mockMvc.perform(patch("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(tag)))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getHeader("Location");

		Map<String, Object> crud = new HashMap<>();
		crud.put("title", "Sample Model Patch");
		crud.put("body", "cc dang iu cute 123");
		crud.put("tags", singletonList(tagLocation));

		this.mockMvc.perform(patch("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isOk());
	}

	@Test
	public void crudPutExample() throws Exception {
		Map<String, String> tag = new HashMap<>();
		tag.put("name", "PUT");

		String tagLocation = this.mockMvc.perform(put("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(tag)))
			.andExpect(status().isAccepted())
			.andReturn()
			.getResponse()
			.getHeader("Location");

		Map<String, Object> crud = new HashMap<>();
		crud.put("title", "Sample Model");
		crud.put("body", "cc dang iu cute 123");
		crud.put("tags", singletonList(tagLocation));

		this.mockMvc.perform(put("/crud/{id}", 10).contentType(MediaTypes.HAL_JSON)
				.content(this.objectMapper.writeValueAsString(crud)))
			.andExpect(status().isAccepted());
	}

	@Test
	public void contextLoads() {
	}
}
