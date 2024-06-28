package com.project.issue.controller;

import com.project.issue.model.Query;
import com.project.issue.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedEndpoint() {
        return ResponseEntity.ok("This is an unprotected endpoint");
    }

    @GetMapping
    public ResponseEntity<List<Query>> getAllQueries() {
        return ResponseEntity.ok(queryService.getAllQueries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Query> getQueryById(@PathVariable Long id) {
        return queryService.getQueryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Query> createQuery(@RequestBody Query query) {
        return ResponseEntity.status(201).body(queryService.createQuery(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Query> updateQuery(@PathVariable Long id, @RequestBody Query query) {
        return queryService.updateQuery(id, query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        if (queryService.deleteQuery(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
