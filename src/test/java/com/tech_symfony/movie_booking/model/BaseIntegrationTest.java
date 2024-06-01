package com.tech_symfony.movie_booking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.UriConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestPropertySource(properties = "spring.jackson.serialization.indent_output=true")
public abstract class BaseIntegrationTest {
	protected MockMvc mockMvc;


	//
//
	protected static MockHttpServletRequestBuilder getAuthenticateHeader(MockHttpServletRequestBuilder request) {
		return request;

//		request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c=");
//
//		return request;
	}

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		UriConfigurer mockMvcRestDocumentationConfigurer =
			MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
				.uris()
				.withScheme("http")
				.withHost("localhost")
				.withPort(8080);

		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(
				mockMvcRestDocumentationConfigurer.and().snippets()
					.withDefaults(CliDocumentation.curlRequest(), HttpDocumentation.httpRequest(), HttpDocumentation.httpResponse())
			)
			.alwaysDo(document("{class-name}/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
			.build();
	}

}
