package com.project.issue.controller;

import com.project.issue.model.Commit;
import com.project.issue.service.VersionService;
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
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class VersionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VersionService versionService;

    @InjectMocks
    private VersionController versionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(versionController).build();
    }

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/users/unprotected"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an unprotected endpoint"));
    }

    @Test
    public void testGetAllCommits() throws Exception {
        Commit commit1 = new Commit();
        commit1.setId("commit1");
        commit1.setMessage("Initial commit");

        Commit commit2 = new Commit();
        commit2.setId("commit2");
        commit2.setMessage("Added new feature");

        when(versionService.getAllCommits()).thenReturn(Arrays.asList(commit1, commit2));

        mockMvc.perform(get("/api/version/commits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("commit1")))
                .andExpect(jsonPath("$[0].message", is("Initial commit")))
                .andExpect(jsonPath("$[1].id", is("commit2")))
                .andExpect(jsonPath("$[1].message", is("Added new feature")));
    }

    @Test
    public void testGetCommitDiff() throws Exception {
        String diff = "diff --git a/file.txt b/file.txt\nindex 83db48f..e4aa190 100644\n--- a/file.txt\n+++ b/file.txt\n@@ -1 +1 @@\n-Hello World\n+Hello Git";

        when(versionService.getCommitDiff(anyString())).thenReturn(Optional.of(diff));

        mockMvc.perform(get("/api/version/commits/commit1/diff")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(diff));
    }

    @Test
    public void testGetCommitDiffNotFound() throws Exception {
        when(versionService.getCommitDiff(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/version/commits/commit1/diff")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllBranches() throws Exception {
        when(versionService.getAllBranches()).thenReturn(Arrays.asList("master", "feature-branch"));

        mockMvc.perform(get("/api/version/branches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("master")))
                .andExpect(jsonPath("$[1]", is("feature-branch")));
    }
}
