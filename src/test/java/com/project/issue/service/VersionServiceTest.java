package com.project.issue.service;

import com.project.issue.model.Commit;
import com.project.issue.repository.VersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class VersionServiceTest {

    @InjectMocks
    private VersionService versionService;

    @Mock
    private VersionRepository versionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCommits() {
        Commit commit1 = new Commit();
        commit1.setId("1");
        commit1.setMessage("Commit 1");

        Commit commit2 = new Commit();
        commit2.setId("2");
        commit2.setMessage("Commit 2");

        when(versionRepository.findAll()).thenReturn(Arrays.asList(commit1, commit2));

        List<Commit> commits = versionService.getAllCommits();
        assertEquals(2, commits.size());
        verify(versionRepository, times(1)).findAll();
    }

    @Test
    public void testGetCommitById() {
        Commit commit = new Commit();
        commit.setId("1");
        commit.setMessage("Test Commit");

        when(versionRepository.findById("1")).thenReturn(Optional.of(commit));

        Optional<Commit> foundCommit = versionService.getCommitById("1");
        assertTrue(foundCommit.isPresent());
        assertEquals("Test Commit", foundCommit.get().getMessage());
    }

    @Test
    public void testGetCommitDiff() {
        Commit commit = new Commit();
        commit.setId("1");
        commit.setMessage("Diff for Commit 1");

        when(versionRepository.findById("1")).thenReturn(Optional.of(commit));

        Optional<String> commitDiff = versionService.getCommitDiff("1");
        assertTrue(commitDiff.isPresent());
        assertEquals("Diff for Commit 1", commitDiff.get());
    }

    @Test
    public void testGetCommitDiffNotFound() {
        when(versionRepository.findById("1")).thenReturn(Optional.empty());

        Optional<String> commitDiff = versionService.getCommitDiff("1");
        assertFalse(commitDiff.isPresent());
    }

    @Test
    public void testGetAllBranches() {
        List<String> branches = versionService.getAllBranches();

        assertEquals(3, branches.size());
        assertTrue(branches.contains("main"));
        assertTrue(branches.contains("dev"));
        assertTrue(branches.contains("feature-branch"));
    }
}
