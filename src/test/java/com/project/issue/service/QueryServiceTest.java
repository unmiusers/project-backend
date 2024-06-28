package com.project.issue.service;

import com.project.issue.model.Query;
import com.project.issue.repository.QueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class QueryServiceTest {

    @InjectMocks
    private QueryService queryService;

    @Mock
    private QueryRepository queryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllQueries() {
        Query query1 = new Query();
        query1.setId(1L);
        query1.setName("Query 1");

        Query query2 = new Query();
        query2.setId(2L);
        query2.setName("Query 2");

        when(queryRepository.findAll()).thenReturn(Arrays.asList(query1, query2));

        assertEquals(2, queryService.getAllQueries().size());
        verify(queryRepository, times(1)).findAll();
    }

    @Test
    public void testGetQueryById() {
        Query query = new Query();
        query.setId(1L);
        query.setName("Test Query");

        when(queryRepository.findById(1L)).thenReturn(Optional.of(query));

        Optional<Query> foundQuery = queryService.getQueryById(1L);
        assertTrue(foundQuery.isPresent());
        assertEquals("Test Query", foundQuery.get().getName());
    }

    @Test
    public void testCreateQuery() {
        Query query = new Query();
        query.setName("New Query");

        when(queryRepository.save(any(Query.class))).thenReturn(query);

        Query createdQuery = queryService.createQuery(query);
        assertEquals("New Query", createdQuery.getName());
        verify(queryRepository, times(1)).save(query);
    }

    @Test
    public void testUpdateQuery() {
        Query existingQuery = new Query();
        existingQuery.setId(1L);
        existingQuery.setName("Existing Query");

        Query updatedQuery = new Query();
        updatedQuery.setName("Updated Query");

        when(queryRepository.findById(1L)).thenReturn(Optional.of(existingQuery));
        when(queryRepository.save(any(Query.class))).thenReturn(existingQuery);

        Optional<Query> result = queryService.updateQuery(1L, updatedQuery);
        assertTrue(result.isPresent());
        assertEquals("Updated Query", result.get().getName());
        verify(queryRepository, times(1)).findById(1L);
        verify(queryRepository, times(1)).save(existingQuery);
    }

    @Test
    public void testDeleteQuery() {
        Query query = new Query();
        query.setId(1L);

        when(queryRepository.findById(1L)).thenReturn(Optional.of(query));

        boolean isDeleted = queryService.deleteQuery(1L);
        assertTrue(isDeleted);
        verify(queryRepository, times(1)).findById(1L);
        verify(queryRepository, times(1)).delete(query);
    }

    @Test
    public void testDeleteQueryNotFound() {
        when(queryRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = queryService.deleteQuery(1L);
        assertFalse(isDeleted);
        verify(queryRepository, times(1)).findById(1L);
        verify(queryRepository, times(0)).delete(any(Query.class));
    }
}
