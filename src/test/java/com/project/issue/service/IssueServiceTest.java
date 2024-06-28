package com.project.issue.service;

import com.project.issue.model.Comment;
import com.project.issue.model.Issue;
import com.project.issue.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllIssues() {
        Issue issue1 = new Issue();
        issue1.setId(1L);
        issue1.setTitle("Issue 1");

        Issue issue2 = new Issue();
        issue2.setId(2L);
        issue2.setTitle("Issue 2");

        when(issueRepository.findAll()).thenReturn(Arrays.asList(issue1, issue2));

        assertEquals(2, issueService.getAllIssues().size());
        verify(issueRepository, times(1)).findAll();
    }

    @Test
    public void testGetIssueById() {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Test Issue");

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));

        Optional<Issue> foundIssue = issueService.getIssueById(1L);
        assertTrue(foundIssue.isPresent());
        assertEquals("Test Issue", foundIssue.get().getTitle());
    }

    @Test
    public void testCreateIssue() {
        Issue issue = new Issue();
        issue.setTitle("New Issue");

        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        Issue createdIssue = issueService.createIssue(issue);
        assertEquals("New Issue", createdIssue.getTitle());
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    public void testUpdateIssue() {
        Issue existingIssue = new Issue();
        existingIssue.setId(1L);
        existingIssue.setTitle("Existing Issue");

        Issue updatedIssue = new Issue();
        updatedIssue.setTitle("Updated Issue");

        when(issueRepository.findById(1L)).thenReturn(Optional.of(existingIssue));
        when(issueRepository.save(any(Issue.class))).thenReturn(existingIssue);

        Optional<Issue> result = issueService.updateIssue(1L, updatedIssue);
        assertTrue(result.isPresent());
        assertEquals("Updated Issue", result.get().getTitle());
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(1)).save(existingIssue);
    }

    @Test
    public void testDeleteIssue() {
        Issue issue = new Issue();
        issue.setId(1L);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));

        boolean isDeleted = issueService.deleteIssue(1L);
        assertTrue(isDeleted);
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(1)).delete(issue);
    }

    @Test
    public void testDeleteIssueNotFound() {
        when(issueRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = issueService.deleteIssue(1L);
        assertFalse(isDeleted);
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(0)).delete(any(Issue.class));
    }

    @Test
    public void testAddComment() {
        Issue issue = new Issue();
        issue.setId(1L);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        Optional<Issue> updatedIssue = issueService.addComment(1L, "Test Comment");
        assertTrue(updatedIssue.isPresent());
        assertEquals(1, updatedIssue.get().getComments().size());
        assertEquals("Test Comment", updatedIssue.get().getComments().get(0).getContent());
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(1)).save(issue);
    }
}
