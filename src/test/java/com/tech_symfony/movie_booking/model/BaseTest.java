package com.tech_symfony.movie_booking.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class BaseTest {

}
