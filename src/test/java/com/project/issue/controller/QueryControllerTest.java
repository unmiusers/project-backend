package com.project.issue.controller;

import com.project.issue.model.Query;
import com.project.issue.service.QueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QueryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QueryService queryService;

    @InjectMocks
    private QueryController queryController;

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/users/unprotected"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an unprotected endpoint"));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(queryController).build();
    }

    @Test
    public void testGetAllQueries() throws Exception {
        Query query1 = new Query();
        query1.setId(1L);
        query1.setName("Query 1");

        Query query2 = new Query();
        query2.setId(2L);
        query2.setName("Query 2");

        when(queryService.getAllQueries()).thenReturn(Arrays.asList(query1, query2));

        mockMvc.perform(get("/api/queries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Query 1")))
                .andExpect(jsonPath("$[1].name", is("Query 2")));
    }

    @Test
    public void testGetQueryById() throws Exception {
        Query query = new Query();
        query.setId(1L);
        query.setName("Test Query");

        when(queryService.getQueryById(1L)).thenReturn(Optional.of(query));

        mockMvc.perform(get("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Query")));
    }

    @Test
    public void testGetQueryByIdNotFound() throws Exception {
        when(queryService.getQueryById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateQuery() throws Exception {
        Query query = new Query();
        query.setName("New Query");

        when(queryService.createQuery(any(Query.class))).thenReturn(query);

        mockMvc.perform(post("/api/queries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Query\", \"filters\": \"{}\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Query")));
    }

    @Test
    public void testUpdateQuery() throws Exception {
        Query query = new Query();
        query.setId(1L);
        query.setName("Updated Query");

        when(queryService.updateQuery(eq(1L), any(Query.class))).thenReturn(Optional.of(query));

        mockMvc.perform(put("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Query\", \"filters\": \"{}\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Query")));
    }

    @Test
    public void testUpdateQueryNotFound() throws Exception {
        when(queryService.updateQuery(eq(1L), any(Query.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Query\", \"filters\": \"{}\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteQuery() throws Exception {
        when(queryService.deleteQuery(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteQueryNotFound() throws Exception {
        when(queryService.deleteQuery(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/queries/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
