package com.project.issue.controller;

import com.project.issue.model.GanttTask;
import com.project.issue.service.GanttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gantt")
public class GanttController {

    @Autowired
    private GanttService ganttService;

    @GetMapping("/unprotected")
    public ResponseEntity<String> unprotectedEndpoint() {
        return ResponseEntity.ok("This is an unprotected endpoint");
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<GanttTask>> getAllGanttTasks() {
        return ResponseEntity.ok(ganttService.getAllGanttTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<GanttTask> createGanttTask(@RequestBody GanttTask ganttTask) {
        return ResponseEntity.status(201).body(ganttService.createGanttTask(ganttTask));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<GanttTask> updateGanttTask(@PathVariable Long id, @RequestBody GanttTask ganttTask) {
        return ganttService.updateGanttTask(id, ganttTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteGanttTask(@PathVariable Long id) {
        if (ganttService.deleteGanttTask(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
