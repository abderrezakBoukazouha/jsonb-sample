package org.winside.integration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.winside.integration.config.TestIngrationEnvConfig;

import static org.hamcrest.Matchers.is;
import static org.winside.entities.ErrorMessage.AGE_ERROR_MESSAGE;


class StudentControllerIntegrationTest extends TestIngrationEnvConfig {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({"THOR,24, 201", "CAPTAIN-AMERICA,25,201", "IRON-MAN,41,400"})
    void shouldAddStudentAndReturnCorrectStatusCode(String name, int age, int status) throws Exception {

        // GIVEN
        String url = String.format("/api/v1/student?name=%s&age=%s", name, age);

        // WHEN
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(url));

        // THEN
        response.andExpect(MockMvcResultMatchers.status().is(status));

        if(status == 201) {
            response.andExpect(MockMvcResultMatchers.jsonPath("$.name", is(name)));
            response.andExpect(MockMvcResultMatchers.jsonPath("$.age", is(age)));
        }

        if (status == 401) {
            response.andExpect(MockMvcResultMatchers.content().string(AGE_ERROR_MESSAGE.get()));
        }
    }
}
