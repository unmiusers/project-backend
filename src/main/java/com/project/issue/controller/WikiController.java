package com.project.issue.controller;

import com.project.issue.model.WikiPage;
import com.project.issue.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wiki")
public class WikiController {

    @Autowired
    private WikiService wikiService;

    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedEndpoint() {
        return ResponseEntity.ok("This is an unprotected endpoint");
    }

    @GetMapping("/pages")
    public ResponseEntity<List<WikiPage>> getAllWikiPages() {
        return ResponseEntity.ok(wikiService.getAllWikiPages());
    }

    @GetMapping("/pages/{id}")
    public ResponseEntity<WikiPage> getWikiPageById(@PathVariable Long id) {
        return wikiService.getWikiPageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pages")
    public ResponseEntity<WikiPage> createWikiPage(@RequestBody WikiPage wikiPage) {
        return ResponseEntity.status(201).body(wikiService.createWikiPage(wikiPage));
    }

    @PutMapping("/pages/{id}")
    public ResponseEntity<WikiPage> updateWikiPage(@PathVariable Long id, @RequestBody WikiPage wikiPage) {
        return wikiService.updateWikiPage(id, wikiPage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/pages/{id}")
    public ResponseEntity<Void> deleteWikiPage(@PathVariable Long id) {
        if (wikiService.deleteWikiPage(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
