package com.project.issue.service;

import com.project.issue.model.Commit;
import com.project.issue.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    public List<Commit> getAllCommits() {
        return versionRepository.findAll();
    }

    public Optional<Commit> getCommitById(String commitId) {
        return versionRepository.findById(commitId);
    }

    public Optional<String> getCommitDiff(String commitId) {
        return versionRepository.findById(commitId)
                .map(commit -> {
                    // Logic to get diff (assuming diff is stored in the message for simplicity)
                    return Optional.of(commit.getMessage());
                }).orElse(Optional.empty());
    }

    public List<String> getAllBranches() {
        // Simplified example, in real scenario, we may fetch branches from an external system like GitHub
        return List.of("main", "dev", "feature-branch");
    }
}
