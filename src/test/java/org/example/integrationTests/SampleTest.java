package org.example.integrationTests;

import org.example.TestContainersAbstractEnv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class SampleTest extends TestContainersAbstractEnv {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void sampleIntegrationTest () throws Exception {
        // When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1"));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
