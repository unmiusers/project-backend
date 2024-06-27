package com.project.issue.service;

import com.project.issue.model.Issue;
import com.project.issue.model.Comment;
import com.project.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Optional<Issue> updateIssue(Long id, Issue issue) {
        return issueRepository.findById(id)
                .map(existingIssue -> {
                    existingIssue.setTitle(issue.getTitle());
                    existingIssue.setDescription(issue.getDescription());
                    existingIssue.setType(issue.getType());
                    existingIssue.setPriority(issue.getPriority());
                    existingIssue.setStatus(issue.getStatus());
                    existingIssue.setAssignee(issue.getAssignee());
                    return issueRepository.save(existingIssue);
                });
    }

    public boolean deleteIssue(Long id) {
        return issueRepository.findById(id)
                .map(issue -> {
                    issueRepository.delete(issue);
                    return true;
                }).orElse(false);
    }

    public Optional<Issue> addComment(Long issueId, String commentContent) {
        return issueRepository.findById(issueId)
                .map(issue -> {
                    Comment comment = new Comment();
                    comment.setContent(commentContent);
                    comment.setAuthor("system"); // Set author appropriately
                    issue.getComments().add(comment);
                    return issueRepository.save(issue);
                });
    }
}
