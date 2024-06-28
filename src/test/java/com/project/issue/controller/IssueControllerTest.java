package com.project.issue.controller;

import com.project.issue.model.Issue;
import com.project.issue.service.IssueService;
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

public class IssueControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IssueService issueService;

    @InjectMocks
    private IssueController issueController;

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/users/unprotected"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an unprotected endpoint"));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(issueController).build();
    }

    @Test
    public void testGetAllIssues() throws Exception {
        Issue issue1 = new Issue();
        issue1.setId(1L);
        issue1.setTitle("Test Issue 1");

        Issue issue2 = new Issue();
        issue2.setId(2L);
        issue2.setTitle("Test Issue 2");

        when(issueService.getAllIssues()).thenReturn(Arrays.asList(issue1, issue2));

        mockMvc.perform(get("/api/issues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Test Issue 1")))
                .andExpect(jsonPath("$[1].title", is("Test Issue 2")));
    }

    @Test
    public void testGetIssueById() throws Exception {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Test Issue");

        when(issueService.getIssueById(1L)).thenReturn(Optional.of(issue));

        mockMvc.perform(get("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Issue")));
    }

    @Test
    public void testGetIssueByIdNotFound() throws Exception {
        when(issueService.getIssueById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateIssue() throws Exception {
        Issue issue = new Issue();
        issue.setTitle("New Issue");

        when(issueService.createIssue(any(Issue.class))).thenReturn(issue);

        mockMvc.perform(post("/api/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Issue\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Issue")));
    }

    @Test
    public void testUpdateIssue() throws Exception {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Updated Issue");

        when(issueService.updateIssue(eq(1L), any(Issue.class))).thenReturn(Optional.of(issue));

        mockMvc.perform(put("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Issue\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Issue")));
    }

    @Test
    public void testUpdateIssueNotFound() throws Exception {
        when(issueService.updateIssue(eq(1L), any(Issue.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Issue\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteIssue() throws Exception {
        when(issueService.deleteIssue(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteIssueNotFound() throws Exception {
        when(issueService.deleteIssue(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddComment() throws Exception {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Test Issue");

        when(issueService.addComment(eq(1L), any(String.class))).thenReturn(Optional.of(issue));

        mockMvc.perform(post("/api/issues/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"This is a comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Issue")));
    }

    @Test
    public void testAddCommentNotFound() throws Exception {
        when(issueService.addComment(eq(1L), any(String.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/issues/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"This is a comment\"}"))
                .andExpect(status().isBadRequest());
    }
}
