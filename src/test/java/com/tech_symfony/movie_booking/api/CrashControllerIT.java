package com.tech_symfony.movie_booking.api;

import com.tech_symfony.movie_booking.model.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

public class CrashControllerIT extends BaseIntegrationTest {
	@Test
	void testTriggerExceptionJson() throws Exception {
		String uri = fromMethodCall(on(CrashController.class).triggerException()).build().toUriString();
		this.mockMvc.perform(get(uri))
			.andExpect(status().isInternalServerError())
			.andDo(document("error-example",
				resourceDetails()
					.description("Get error links."),
				responseFields(subsectionWithPath("errors").description("The error message"),
					subsectionWithPath("className").description("The path that caused the error"),
					subsectionWithPath("timestamp").description("The timestamp of the error"))
			));
	}


}
