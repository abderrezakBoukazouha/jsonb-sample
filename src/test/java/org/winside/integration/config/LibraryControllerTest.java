package org.winside.integration.config;


import org.junit.jupiter.api.Test;
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
class LibraryControllerTest {
    
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


    @Test
    void givenLibraryJson_whenSaved_shouldGetSavedLibrary() throws Exception {

        // GIVEN
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/library.json")));

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/library")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[0]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[0].bookId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[0].authors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[1]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[1].authors").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[1].authors[0]").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.library.books[1].authors[1].awards").isArray());
    }
}
