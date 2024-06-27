package com.project.issue.controller;

import com.project.issue.model.TimeLog;
import com.project.issue.service.TimeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timelog")
public class TimeLogController {

    @Autowired
    private TimeLogService timeLogService;

    @GetMapping
    public ResponseEntity<List<TimeLog>> getAllTimeLogs() {
        return ResponseEntity.ok(timeLogService.getAllTimeLogs());
    }

    @PostMapping
    public ResponseEntity<TimeLog> createTimeLog(@RequestBody TimeLog timeLog) {
        return ResponseEntity.status(201).body(timeLogService.createTimeLog(timeLog));
    }

    @GetMapping("/reports")
    public ResponseEntity<List<TimeLog>> getTimeLogReports() {
        return ResponseEntity.ok(timeLogService.getTimeLogReports());
    }

    @GetMapping("/visualization")
    public ResponseEntity<List<Map<String, Object>>> getTimeLogVisualization() {
        return ResponseEntity.ok(timeLogService.getTimeLogVisualization());
    }
}