package com.criticalsoftware.personservice.controller;

import com.criticalsoftware.personservice.controller.form.PersonForm;
import com.criticalsoftware.personservice.enums.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String PERSON_NAME = "Patrício Correia";

    @Test
    public void testCreatePerson() throws Exception {
        PersonForm form = new PersonForm(PERSON_NAME, "Brasileiro");
        String bodyContent = objectMapper.writeValueAsString(form);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    public void testCreateDuplicatedPerson() throws Exception {
        PersonForm form = new PersonForm(PERSON_NAME, "Brasileiro");
        String bodyContent = objectMapper.writeValueAsString(form);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(ErrorType.PERROR_001.getLabel()));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        PersonForm form = new PersonForm(PERSON_NAME, "Portuguesa");
        String bodyContent = objectMapper.writeValueAsString(form);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/person/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PERSON_NAME))
                .andExpect(jsonPath("$.nationality").value("Portuguesa"));
    }

    @Test
    public void testUpdateNotFoundedPerson() throws Exception {
        PersonForm form = new PersonForm(PERSON_NAME, "Portuguesa");
        String bodyContent = objectMapper.writeValueAsString(form);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/person/{id}", "100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ErrorType.PERROR_002.getLabel()));
    }

    @Test
    public void testFindPersonById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/person/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindPersonByIdNotFounded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/person/{id}", 15L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ErrorType.PERROR_002.getLabel()));
    }

    @Test
    public void testGetPeople() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/person/{id}", 1L))
                .andExpect(status().isOk());
    }
}

