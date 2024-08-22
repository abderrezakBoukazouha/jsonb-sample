package org.winside.integration.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {


    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("test")
            .withReuse(true);

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer ::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer ::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer ::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        String firstJson = new String(Files.readAllBytes(Paths.get("src/test/resources/library.json")));


        mockMvc.perform(MockMvcRequestBuilders.post("/library")
                .contentType(MediaType.APPLICATION_JSON)
                .content(firstJson));
    }


    @ParameterizedTest
    @ValueSource(strings = {"978-9849-431-47-8", "978-4285-512-83-6", "978-6211-523-95-2"})
    void givenNoneEmptyDataBase_whenSearchingViaIsbn_shouldReturnListOfBooks(String isbn) throws Exception {

        // WHEN 
        mockMvc.perform(MockMvcRequestBuilders.get("/book?isbn=%s".formatted(isbn))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publicationDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].authorId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].lastName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].birthYear").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].nationality").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].awards").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].isAlive").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[1]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[1].authorId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[1].awards").isArray());
    }

    @Test
    void givenNoneEmptyDataBase_whenSearchingInvalidIsbn_shouldReturnNotFound() throws Exception {
        // Given
        final String isbn = "INCORRECT_ISBN";

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/book?isbn=%s".formatted(isbn))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
