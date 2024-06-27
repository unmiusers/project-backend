package com.project.issue.controller;

import com.project.issue.model.Commit;
import com.project.issue.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @GetMapping("/commits")
    public ResponseEntity<List<Commit>> getAllCommits() {
        return ResponseEntity.ok(versionService.getAllCommits());
    }

    @GetMapping("/commits/{commitId}/diff")
    public ResponseEntity<String> getCommitDiff(@PathVariable String commitId) {
        return versionService.getCommitDiff(commitId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/branches")
    public ResponseEntity<List<String>> getAllBranches() {
        return ResponseEntity.ok(versionService.getAllBranches());
    }
}
